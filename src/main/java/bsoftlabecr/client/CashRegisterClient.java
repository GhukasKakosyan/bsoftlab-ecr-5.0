package bsoftlabecr.client;

import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.ResponseType;

import bsoftlabecr.exception.ConnectionException;
import bsoftlabecr.exception.DecryptionException;
import bsoftlabecr.exception.EncryptionException;
import bsoftlabecr.exception.InitialisationException;
import bsoftlabecr.exception.NetworkException;
import bsoftlabecr.exception.OperationException;

import bsoftlabecr.request.cashier.CashiersAndDepsRequest;
import bsoftlabecr.request.cashier.LoginCashierRequest;
import bsoftlabecr.request.cashier.LogoutCashierRequest;

import bsoftlabecr.request.headerfooter.HeaderFooterRequest;

import bsoftlabecr.request.receipt.prepayment.ExistPrepaymentRequest;
import bsoftlabecr.request.receipt.prepayment.NewPrepaymentRequest;
import bsoftlabecr.request.receipt.returns.prepayment.ExistReturnPrepaymentRequest;
import bsoftlabecr.request.receipt.returns.prepayment.NewOriginalReturnPrepaymentRequest;
import bsoftlabecr.request.receipt.returns.prepayment.NewPartialReturnPrepaymentRequest;
import bsoftlabecr.request.receipt.returns.sale.ExistReturnSaleRequest;
import bsoftlabecr.request.receipt.returns.sale.NewOriginalReturnSaleRequest;
import bsoftlabecr.request.receipt.returns.sale.NewPartialReturnSaleRequest;
import bsoftlabecr.request.receipt.sale.ExistSaleRequest;
import bsoftlabecr.request.receipt.sale.NewSaleRequest;

import bsoftlabecr.request.report.FiscalReportRequest;

import bsoftlabecr.response.cashier.CashiersAndDepsResponse;
import bsoftlabecr.response.cashier.LoginCashierResponse;
import bsoftlabecr.response.cashier.LogoutCashierResponse;

import bsoftlabecr.response.headerfooter.HeaderFooterResponse;

import bsoftlabecr.response.receipt.prepayment.ExistPrepaymentResponse;
import bsoftlabecr.response.receipt.prepayment.NewPrepaymentResponse;
import bsoftlabecr.response.receipt.returns.prepayment.ExistReturnPrepaymentResponse;
import bsoftlabecr.response.receipt.returns.prepayment.NewOriginalReturnPrepaymentResponse;
import bsoftlabecr.response.receipt.returns.prepayment.NewPartialReturnPrepaymentResponse;
import bsoftlabecr.response.receipt.returns.sale.ExistReturnSaleResponse;
import bsoftlabecr.response.receipt.returns.sale.NewOriginalReturnSaleResponse;
import bsoftlabecr.response.receipt.returns.sale.NewPartialReturnSaleResponse;
import bsoftlabecr.response.receipt.sale.ExistSaleResponse;
import bsoftlabecr.response.receipt.sale.NewSaleResponse;

import bsoftlabecr.response.report.FiscalReportResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CashRegisterClient {

    private static final String TRIPLE_DES_ALG = "DESede";
    private static final String CIPHER_ALGORITHM_SPEC = TRIPLE_DES_ALG + "/ECB/PKCS7Padding";
    private static final byte[] PROTOCOL_VERSION = new byte[]{(byte)0x00, (byte)0x05};

    private static final byte[] REQUEST_HEADER = new byte[]{
            (byte)0xD5, (byte)0x80, (byte)0xD4, (byte)0xB4, (byte)0xD5, (byte)0x84,
            PROTOCOL_VERSION[0], PROTOCOL_VERSION[1]
    };

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final short REQUEST_HEADER_OP_LENGTH = 2;
    private static final short REQUEST_HEADER_PAYLOAD_LENGHT = 2;
    private static final short REQUEST_HEADER_LENGTH = (short)(REQUEST_HEADER.length +
            REQUEST_HEADER_OP_LENGTH + REQUEST_HEADER_PAYLOAD_LENGHT);

    private static final byte ZERO = 0;
    private static final byte OPER_CASHIERS_AND_DEPS_LIST = 1;
    private static final byte OPER_CASHIER_LOGIN = 2;
    private static final byte OPER_CASHIER_LOGOUT = 3;
    private static final byte OPER_NEW_SALE_PREPAYMENT_RECEIPT = 4;
    private static final byte OPER_NEW_RETURN_RECEIPT = 6;
    private static final byte OPER_SETUP_HEADERFOOTER = 7;
    private static final byte OPER_PRINT_FISCAL_REPORT = 9;
    private static final byte OPER_GET_EXISTED_RECEIPT = 10;

    private byte[] passwordKey;
    private byte[] sessionKey;

    private Constants constants;
    private Integer seq = 0;
    private ObjectMapper objectMapper;
    private SocketChannel socketChannel;

    public byte[] getPasswordKey() {return this.passwordKey;}
    public byte[] getSessionKey() {return this.sessionKey;}

    public Integer getSeq() {
        return ++this.seq;
    }
    public void setSessionKey (byte[] sessionKey) {this.sessionKey = sessionKey;}

    public CashRegisterClient(Constants constants) throws InitialisationException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            this.constants = constants;
            byte[] passwordKey = messageDigest.digest(this.constants.getPassword().getBytes());
            this.passwordKey = Arrays.copyOf(passwordKey, 24);
            this.objectMapper = new ObjectMapper();
            this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new InitialisationException(ResponseType.INITIALISATION_ERROR.getCode());
        }
    }

    public void connect() throws ConnectionException {
        try {
            String ip = this.constants.getIp();
            Integer port = this.constants.getPort();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            this.socketChannel = SocketChannel.open();
            this.socketChannel.connect(inetSocketAddress);
        } catch (IOException ioException) {
            throw new ConnectionException(ResponseType.CONNECTION_ERROR.getCode());
        }
    }

    public void disconnect() throws ConnectionException {
        try {
            this.socketChannel.shutdownInput();
            this.socketChannel.shutdownOutput();
            this.socketChannel.close();
        } catch (IOException ioException) {
            throw new ConnectionException(ResponseType.DISCONNECTION_ERROR.getCode());
        }
    }

    /**
     * Encrypts byte array using password key
     * @param byteArray               Byte Array to be encrypted
     * @return                        Encrypted byte array using password key
     * @throws EncryptionException    Encryption Exception
     */
    private byte[] getEncryptedByteArrayByPasswordKey(byte[] byteArray)
            throws EncryptionException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(this.passwordKey, TRIPLE_DES_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(byteArray);
        } catch (BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchAlgorithmException |
                NoSuchPaddingException exception) {
            throw new EncryptionException(ResponseType.ENCRYPTION_ERROR.getCode());
        }
    }

    /**
     * Encrypts byte array using session key
     * @param byteArray               Byte Array to be encrypted
     * @return                        Encrypted byte array using session key
     * @throws EncryptionException    Encryption Exception
     */
    private byte[] getEncryptedByteArrayBySessionKey(byte[] byteArray)
            throws EncryptionException {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(this.sessionKey, TRIPLE_DES_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(byteArray);
        } catch (BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchAlgorithmException  |
                NoSuchPaddingException exception) {
            throw new EncryptionException(ResponseType.ENCRYPTION_ERROR.getCode());
        }
    }

    /**
     * Decrypts byte array using password key
     * @param encryptedByteArray      Byte array to be decrypted
     * @return                        Decrypted byte array using password key
     * @throws DecryptionException    Decryption Exception
     */
    private byte[] getDecryptedByteArrayByPasswordKey(byte[] encryptedByteArray)
            throws DecryptionException {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(this.passwordKey, TRIPLE_DES_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(encryptedByteArray);
        } catch (BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchAlgorithmException |
                NoSuchPaddingException exception) {
            throw new DecryptionException(ResponseType.DECRYPTION_ERROR.getCode());
        }
    }

    /**
     * Decrypts byte array using session key
     * @param encryptedByteArray      Byte array to be decrypted
     * @return                        Decrypted byte array using session key
     * @throws DecryptionException    Decryption Exception
     */
    private byte[] getDecryptedByteArrayBySessionKey(byte[] encryptedByteArray)
            throws DecryptionException {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(this.sessionKey, TRIPLE_DES_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(encryptedByteArray);
        } catch (BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchAlgorithmException |
                NoSuchPaddingException exception) {
            throw new DecryptionException(ResponseType.DECRYPTION_ERROR.getCode());
        }
    }

    /**
     * Send encrypted ByteBuffer of request and receive encrypted ByteBuffer of response
     * @param encryptedRequestByteBuffer    encrypted ByteBuffer of request to be sent
     * @return                              encrypted ByteBuffer of response to be received
     * @throws NetworkException             Network Exception
     * @throws OperationException           Invalid response code Exception
     */
    private ByteBuffer sendRequestReceiveResponse(
            ByteBuffer encryptedRequestByteBuffer)
            throws NetworkException, OperationException {

        try {
            encryptedRequestByteBuffer.flip();
            this.socketChannel.write(encryptedRequestByteBuffer); // Encrypted request is sent
            ByteBuffer headerEncryptedResponseByteBuffer = ByteBuffer.allocate(11); // Prepare ByteBuffer for header of encrypted response
            this.socketChannel.read(headerEncryptedResponseByteBuffer); // Reads header of encrypted response;
            headerEncryptedResponseByteBuffer.flip(); // Match Endianness

            headerEncryptedResponseByteBuffer.get(); // Get protocol version first byte
            headerEncryptedResponseByteBuffer.get(); // Get protocol version second byte

            headerEncryptedResponseByteBuffer.get(); // Get software version first byte
            headerEncryptedResponseByteBuffer.get(); // Get software version second byte
            headerEncryptedResponseByteBuffer.get(); // Get software version third byte

            Integer responseCode = headerEncryptedResponseByteBuffer.getShort() & 0xFFFF; // Get two bytes of response code as integer
            if (!responseCode.equals(ResponseType.OK.getCode())) {
                throw new OperationException(responseCode); // Generate OperationException if not OK
            }

            int length = headerEncryptedResponseByteBuffer.getShort() & 0xFFFF; // Get two bytes of response length as integer
            headerEncryptedResponseByteBuffer.get(); // Get first byte of two reserved bytes
            headerEncryptedResponseByteBuffer.get(); // Get second byte of two reserved bytes

            ByteBuffer encryptedResponseByteBuffer = ByteBuffer.allocate(length); // Prepare ByteBuffer for body of encrypted response
            this.socketChannel.read(encryptedResponseByteBuffer); // Reads body of encrypted response
            return encryptedResponseByteBuffer;
        } catch (IOException ioException) {
            throw new NetworkException(ResponseType.NETWORK_ERROR.getCode());
        }
    }

    /**
     * Get cashiers and departments list
     * @param cashiersAndDepsRequest            Cashiers and departments list request
     * @return                                  Cashiers and departments list response
     * @throws OperationException               Cash Register Exception
     */
    public CashiersAndDepsResponse getCashiersAndDepsResponse(
            CashiersAndDepsRequest cashiersAndDepsRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(cashiersAndDepsRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayByPasswordKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;

            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_CASHIERS_AND_DEPS_LIST).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);

            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayByPasswordKey(encryptedResponseByteArray);
            CashiersAndDepsResponse cashiersAndDepsResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            CashiersAndDepsResponse.class);
            cashiersAndDepsResponse.setResponseCode(ResponseType.OK.getCode());
            return cashiersAndDepsResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Logs in given cashier and returns cashier response with session key
     * @param loginCashierRequest               Cashier login request
     * @return                                  Cashier login response with session key
     * @throws OperationException               Cash Register Exception
     */
    public LoginCashierResponse getLoginCashierResponse(
            LoginCashierRequest loginCashierRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(loginCashierRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayByPasswordKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_CASHIER_LOGIN).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);

            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayByPasswordKey(encryptedResponseByteArray);
            LoginCashierResponse loginCashierResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            LoginCashierResponse.class);
            loginCashierResponse.setResponseCode(ResponseType.OK.getCode());
            return loginCashierResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Logout current cashier
     * @param logoutCashierRequest              Logout cashier request
     * @return                                  Logout cashier response
     * @throws OperationException               Cash Register Exception
     */
    public LogoutCashierResponse getLogoutCashierResponse(
            LogoutCashierRequest logoutCashierRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(logoutCashierRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_CASHIER_LOGOUT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            this.sessionKey = null;
            LogoutCashierResponse logoutCashierResponse = new LogoutCashierResponse();
            logoutCashierResponse.setResponseCode(ResponseType.OK.getCode());
            return logoutCashierResponse;
        } catch (JsonProcessingException jsonProcessingException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (EncryptionException | NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Setup header and footer for request
     * @param headerFooterRequest               Setup headers and footers request
     * @return                                  Setup headers and footers response
     * @throws OperationException               Cash Register Exception
     */
    public HeaderFooterResponse setupHeaderFooter(
            HeaderFooterRequest headerFooterRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(headerFooterRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_SETUP_HEADERFOOTER).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            HeaderFooterResponse headerFooterResponse = new HeaderFooterResponse();
            headerFooterResponse.setResponseCode(ResponseType.OK.getCode());
            return headerFooterResponse;
        } catch (JsonProcessingException jsonProcessingException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (EncryptionException | NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Prints fiscal report.
     * @param fiscalReportRequest               Fiscal Report Request
     * @return                                  Fiscal Report Response
     * @throws OperationException               Cash Register Exception
     **/
    public FiscalReportResponse printFiscalReport(
            FiscalReportRequest fiscalReportRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(fiscalReportRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_PRINT_FISCAL_REPORT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            FiscalReportResponse fiscalReportResponse = new FiscalReportResponse();
            fiscalReportResponse.setResponseCode(ResponseType.OK.getCode());
            return fiscalReportResponse;
        } catch (JsonProcessingException jsonProcessingException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (EncryptionException | NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get existing prepayment request response
     * @param existPrepaymentRequest            Existing Prepayment Request
     * @return                                  Existing Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistPrepaymentResponse getExistPrepaymentResponse(
            ExistPrepaymentRequest existPrepaymentRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(existPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_GET_EXISTED_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray
                    = this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            ExistPrepaymentResponse existPrepaymentResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            ExistPrepaymentResponse.class);
            existPrepaymentResponse.setResponseCode(ResponseType.OK.getCode());
            return existPrepaymentResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (EncryptionException | DecryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get existing return prepayment request response
     * @param existReturnPrepaymentRequest      Existing Return Prepayment Request
     * @return                                  Existing Return Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistReturnPrepaymentResponse getExistReturnPrepaymentResponse(
            ExistReturnPrepaymentRequest existReturnPrepaymentRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(existReturnPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_GET_EXISTED_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            ExistReturnPrepaymentResponse existReturnPrepaymentResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            ExistReturnPrepaymentResponse.class);
            existReturnPrepaymentResponse.setResponseCode(ResponseType.OK.getCode());
            return existReturnPrepaymentResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get existing return sale request response
     * @param existReturnSaleRequest            Existing Return Sale Request
     * @return                                  Existing Return Sale Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistReturnSaleResponse getExistReturnSaleResponse(
            ExistReturnSaleRequest existReturnSaleRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(existReturnSaleRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_GET_EXISTED_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            ExistReturnSaleResponse existReturnSaleResponse = this.objectMapper
                    .readValue(decryptedResponseByteArray,
                            ExistReturnSaleResponse.class);
            existReturnSaleResponse.setResponseCode(ResponseType.OK.getCode());
            return existReturnSaleResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get existing sale request response
     * @param existSaleRequest                  Existing Sale Request
     * @return                                  Existing Sale Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistSaleResponse getExistSaleResponse(ExistSaleRequest existSaleRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(existSaleRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_GET_EXISTED_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            ExistSaleResponse existSaleResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            ExistSaleResponse.class);
            existSaleResponse.setResponseCode(ResponseType.OK.getCode());
            return existSaleResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new prepayment request response
     * @param newPrepaymentRequest              New Prepayment Request
     * @return                                  New Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public NewPrepaymentResponse getNewPrepaymentResponse(
            NewPrepaymentRequest newPrepaymentRequest) throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_SALE_PREPAYMENT_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewPrepaymentResponse newPrepaymentResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            NewPrepaymentResponse.class);
            newPrepaymentResponse.setResponseCode(ResponseType.OK.getCode());
            return newPrepaymentResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new partial return prepayment request response
     * @param newPartialReturnPrepaymentRequest New Partial Return Prepayment Request
     * @return                                  New Partial Return Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public NewPartialReturnPrepaymentResponse getNewPartialReturnPrepaymentResponse(
            NewPartialReturnPrepaymentRequest newPartialReturnPrepaymentRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newPartialReturnPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_RETURN_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewPartialReturnPrepaymentResponse newPartialReturnPrepaymentResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            NewPartialReturnPrepaymentResponse.class);
            newPartialReturnPrepaymentResponse.setResponseCode(ResponseType.OK.getCode());
            return newPartialReturnPrepaymentResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new original return prepayment request response
     * @param newOriginalReturnPrepaymentRequest  New Original Return Prepayment Request
     * @return                                    New Original Return Prepayment Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewOriginalReturnPrepaymentResponse getNewOriginalReturnPrepaymentResponse(
            NewOriginalReturnPrepaymentRequest newOriginalReturnPrepaymentRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newOriginalReturnPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_RETURN_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewOriginalReturnPrepaymentResponse newOriginalReturnPrepaymentResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            NewOriginalReturnPrepaymentResponse.class);
            newOriginalReturnPrepaymentResponse.setResponseCode(ResponseType.OK.getCode());
            return newOriginalReturnPrepaymentResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new partial return sale request response
     * @param newPartialReturnSaleRequest         New Partial Return Sale Request
     * @return                                    New Partial Return Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewPartialReturnSaleResponse getNewPartialReturnSaleResponse(
            NewPartialReturnSaleRequest newPartialReturnSaleRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newPartialReturnSaleRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_RETURN_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewPartialReturnSaleResponse newPartialReturnSaleResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            NewPartialReturnSaleResponse.class);
            newPartialReturnSaleResponse.setResponseCode(ResponseType.OK.getCode());
            return newPartialReturnSaleResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new original return sale request response
     * @param newOriginalReturnSaleRequest        New Original Return Sale Request
     * @return                                    New Original Return Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewOriginalReturnSaleResponse getNewOriginalReturnSaleResponse(
            NewOriginalReturnSaleRequest newOriginalReturnSaleRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newOriginalReturnSaleRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_RETURN_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray =
                    this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewOriginalReturnSaleResponse newOriginalReturnSaleResponse =
                    this.objectMapper.readValue(decryptedResponseByteArray,
                            NewOriginalReturnSaleResponse.class);
            newOriginalReturnSaleResponse.setResponseCode(ResponseType.OK.getCode());
            return newOriginalReturnSaleResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }

    /**
     * Get new sale request response
     * @param newSaleRequest                      New Sale Request
     * @return                                    New Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewSaleResponse getNewSaleResponse(NewSaleRequest newSaleRequest)
            throws OperationException {

        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newSaleRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short) encryptedRequestByteArray.length;
            ByteBuffer encryptedRequestByteBuffer =
                    ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
            encryptedRequestByteBuffer
                    .put(REQUEST_HEADER)
                    .put(OPER_NEW_SALE_PREPAYMENT_RECEIPT).put(ZERO)
                    .putShort(length).put(encryptedRequestByteArray);
            ByteBuffer encryptedResponseByteBuffer =
                    this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseByteArray = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseByteArray
                    = this.getDecryptedByteArrayBySessionKey(encryptedResponseByteArray);
            NewSaleResponse newSaleResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewSaleResponse.class);
            newSaleResponse.setResponseCode(ResponseType.OK.getCode());
            return newSaleResponse;
        } catch (IOException ioException) {
            throw new OperationException(ResponseType.SERIALIZATION_ERROR.getCode());
        } catch (DecryptionException | EncryptionException |
                NetworkException operationException) {
            throw new OperationException(operationException.getResponseCode());
        }
    }
}