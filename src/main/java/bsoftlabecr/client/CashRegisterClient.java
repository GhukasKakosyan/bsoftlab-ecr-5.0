package bsoftlabecr.client;

import bsoftlabecr.entity.Constants;
import bsoftlabecr.entity.ResponseCodes;

import bsoftlabecr.exception.CommonResponseException;
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

import bsoftlabecr.response.general.CommonResponse;
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
            throw new InitialisationException(noSuchAlgorithmException.getMessage());
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
            throw new ConnectionException(ioException.getMessage());
        }
    }

    public void disconnect() throws ConnectionException {
        try {
            this.socketChannel.shutdownInput();
            this.socketChannel.shutdownOutput();
            this.socketChannel.close();
        } catch (IOException ioException) {
            throw new ConnectionException(ioException.getMessage());
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
                InvalidKeyException | NoSuchPaddingException |
                NoSuchAlgorithmException exception) {
            throw new EncryptionException(exception.getMessage());
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
            throw new EncryptionException(exception.getMessage());
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
            throw new DecryptionException(exception.getMessage());
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
            throw new DecryptionException(exception.getMessage());
        }
    }

    /**
     * Send encrypted ByteBuffer of request and receive encrypted ByteBuffer of response
     * @param encryptedRequestByteBuffer    encrypted ByteBuffer of request to be sent
     * @return                              encrypted ByteBuffer of response to be received
     * @throws NetworkException             Network Exception
     * @throws CommonResponseException      Invalid response code Exception
     */
    private ByteBuffer sendRequestReceiveResponse(
            ByteBuffer encryptedRequestByteBuffer)
            throws NetworkException, CommonResponseException {

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
            if (!responseCode.equals(ResponseCodes.OK.getCode())) {
                CommonResponse commonResponse = new CommonResponse(responseCode);
                throw new CommonResponseException(commonResponse); // Generate CommonResponseException if not OK
            }

            int length = headerEncryptedResponseByteBuffer.getShort() & 0xFFFF; // Get two bytes of response length as integer
            headerEncryptedResponseByteBuffer.get(); // Get first byte of two reserved bytes
            headerEncryptedResponseByteBuffer.get(); // Get second byte of two reserved bytes

            ByteBuffer encryptedResponseByteBuffer = ByteBuffer.allocate(length); // Prepare ByteBuffer for body of encrypted response
            this.socketChannel.read(encryptedResponseByteBuffer); // Reads body of encrypted response
            return encryptedResponseByteBuffer;
        } catch (IOException ioException) {
            throw new NetworkException(ioException.getMessage());
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

        CashiersAndDepsResponse cashiersAndDepsResponse;
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
            cashiersAndDepsResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, CashiersAndDepsResponse.class);
            cashiersAndDepsResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException){
            cashiersAndDepsResponse = new CashiersAndDepsResponse();
            cashiersAndDepsResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return cashiersAndDepsResponse;
    }

    /**
     * Logs in given cashier and returns cashier response with session key
     * @param loginCashierRequest               Cashier login request
     * @return                                  Cashier login response with session key
     * @throws OperationException               Cash Register Exception
     */
    public LoginCashierResponse getLoginCashierResponse(
            LoginCashierRequest loginCashierRequest) throws OperationException {

        LoginCashierResponse loginCashierResponse;
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
            loginCashierResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, LoginCashierResponse.class);
            loginCashierResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch(CommonResponseException commonResponseException){
            loginCashierResponse = new LoginCashierResponse();
            loginCashierResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return loginCashierResponse;
    }

    /**
     * Logout current cashier
     * @param logoutCashierRequest              Logout cashier request
     * @return                                  Logout cashier response
     * @throws OperationException               Cash Register Exception
     */
    public LogoutCashierResponse getLogoutCashierResponse(
            LogoutCashierRequest logoutCashierRequest) throws OperationException {

        LogoutCashierResponse logoutCashierResponse = new LogoutCashierResponse();
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
            logoutCashierResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (EncryptionException | JsonProcessingException |
                NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch(CommonResponseException commonResponseException) {
            logoutCashierResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return logoutCashierResponse;
    }

    /**
     * Setup header and footer for receipt
     * @param headerFooterRequest               Setup headers and footers request
     * @return                                  Setup headers and footers response
     * @throws OperationException               Cash Register Exception
     */
    public HeaderFooterResponse setupHeaderFooter(
            HeaderFooterRequest headerFooterRequest) throws OperationException {

        HeaderFooterResponse headerFooterResponse = new HeaderFooterResponse();
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
            headerFooterResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (EncryptionException | JsonProcessingException |
                NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            headerFooterResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return headerFooterResponse;
    }

    /**
     * Prints fiscal report.
     * @param fiscalReportRequest               Fiscal Report Request
     * @return                                  Fiscal Report Response
     * @throws OperationException               Cash Register Exception
     **/
    public FiscalReportResponse printFiscalReport(
            FiscalReportRequest fiscalReportRequest) throws OperationException {

        FiscalReportResponse fiscalReportResponse = new FiscalReportResponse();
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
            fiscalReportResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (EncryptionException | JsonProcessingException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            fiscalReportResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return fiscalReportResponse;
    }

    /**
     * Get existing prepayment receipt response
     * @param existPrepaymentRequest            Existing Prepayment Request
     * @return                                  Existing Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistPrepaymentResponse getExistPrepaymentResponse(
            ExistPrepaymentRequest existPrepaymentRequest) throws OperationException {

        ExistPrepaymentResponse existPrepaymentResponse;
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
            existPrepaymentResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, ExistPrepaymentResponse.class);
            existPrepaymentResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (EncryptionException | DecryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            existPrepaymentResponse = new ExistPrepaymentResponse();
            existPrepaymentResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return existPrepaymentResponse;
    }

    /**
     * Get existing return prepayment receipt response
     * @param existReturnPrepaymentRequest      Existing Return Prepayment Request
     * @return                                  Existing Return Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistReturnPrepaymentResponse getExistReturnPrepaymentResponse(
            ExistReturnPrepaymentRequest existReturnPrepaymentRequest)
            throws OperationException {

        ExistReturnPrepaymentResponse existReturnPrepaymentResponse;
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
            existReturnPrepaymentResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, ExistReturnPrepaymentResponse.class);
            existReturnPrepaymentResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            existReturnPrepaymentResponse = new ExistReturnPrepaymentResponse();
            existReturnPrepaymentResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return existReturnPrepaymentResponse;
    }

    /**
     * Get existing return sale receipt response
     * @param existReturnSaleRequest            Existing Return Sale Request
     * @return                                  Existing Return Sale Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistReturnSaleResponse getExistReturnSaleResponse(
            ExistReturnSaleRequest existReturnSaleRequest)
            throws OperationException {

        ExistReturnSaleResponse existReturnSaleResponse;
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
            existReturnSaleResponse = this.objectMapper
                    .readValue(decryptedResponseByteArray, ExistReturnSaleResponse.class);
            existReturnSaleResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            existReturnSaleResponse = new ExistReturnSaleResponse();
            existReturnSaleResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return existReturnSaleResponse;
    }

    /**
     * Get existing sale receipt response
     * @param existSaleRequest                  Existing Sale Request
     * @return                                  Existing Sale Response
     * @throws OperationException               Cash Register Exception
     */
    public ExistSaleResponse getExistSaleResponse(
            ExistSaleRequest existSaleRequest) throws OperationException {

        ExistSaleResponse existSaleResponse;
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
            existSaleResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, ExistSaleResponse.class);
            existSaleResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            existSaleResponse = new ExistSaleResponse();
            existSaleResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return existSaleResponse;
    }

    /**
     * Get new prepayment receipt response
     * @param newPrepaymentRequest              New Prepayment Request
     * @return                                  New Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public NewPrepaymentResponse getNewPrepaymentResponse(
            NewPrepaymentRequest newPrepaymentRequest) throws OperationException {

        NewPrepaymentResponse newPrepaymentResponse;
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
            newPrepaymentResponse = this.objectMapper
                    .readValue(decryptedResponseByteArray, NewPrepaymentResponse.class);
            newPrepaymentResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch(CommonResponseException commonResponseException) {
            newPrepaymentResponse = new NewPrepaymentResponse();
            newPrepaymentResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newPrepaymentResponse;
    }

    /**
     * Get new partial return prepayment receipt response
     * @param newPartialReturnPrepaymentRequest New Partial Return Prepayment Request
     * @return                                  New Partial Return Prepayment Response
     * @throws OperationException               Cash Register Exception
     */
    public NewPartialReturnPrepaymentResponse getNewPartialReturnPrepaymentResponse(
            NewPartialReturnPrepaymentRequest newPartialReturnPrepaymentRequest) throws OperationException {
        NewPartialReturnPrepaymentResponse newPartialReturnPrepaymentResponse;
        try {
            byte[] requestByteArray =
                    this.objectMapper.writeValueAsBytes(newPartialReturnPrepaymentRequest);
            byte[] encryptedRequestByteArray =
                    this.getEncryptedByteArrayBySessionKey(requestByteArray);
            short length = (short)encryptedRequestByteArray.length;
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
            newPartialReturnPrepaymentResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewPartialReturnPrepaymentResponse.class);
            newPartialReturnPrepaymentResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            newPartialReturnPrepaymentResponse = new NewPartialReturnPrepaymentResponse();
            newPartialReturnPrepaymentResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newPartialReturnPrepaymentResponse;
    }

    /**
     * Get new original return prepayment receipt response
     * @param newOriginalReturnPrepaymentRequest  New Original Return Prepayment Request
     * @return                                    New Original Return Prepayment Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewOriginalReturnPrepaymentResponse getNewOriginalReturnPrepaymentResponse(
            NewOriginalReturnPrepaymentRequest newOriginalReturnPrepaymentRequest)
            throws OperationException {

        NewOriginalReturnPrepaymentResponse newOriginalReturnPrepaymentResponse;
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
            newOriginalReturnPrepaymentResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewOriginalReturnPrepaymentResponse.class);
            newOriginalReturnPrepaymentResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            newOriginalReturnPrepaymentResponse = new NewOriginalReturnPrepaymentResponse();
            newOriginalReturnPrepaymentResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newOriginalReturnPrepaymentResponse;
    }

    /**
     * Get new partial return sale receipt response
     * @param newPartialReturnSaleRequest         New Partial Return Sale Request
     * @return                                    New Partial Return Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewPartialReturnSaleResponse getNewPartialReturnSaleResponse(
            NewPartialReturnSaleRequest newPartialReturnSaleRequest)
            throws OperationException {

        NewPartialReturnSaleResponse newPartialReturnSaleResponse;
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
            newPartialReturnSaleResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewPartialReturnSaleResponse.class);
            newPartialReturnSaleResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch(CommonResponseException commonResponseException) {
            newPartialReturnSaleResponse = new NewPartialReturnSaleResponse();
            newPartialReturnSaleResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newPartialReturnSaleResponse;
    }

    /**
     * Get new original return sale receipt response
     * @param newOriginalReturnSaleRequest        New Original Return Sale Request
     * @return                                    New Original Return Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewOriginalReturnSaleResponse getNewOriginalReturnSaleResponse(
            NewOriginalReturnSaleRequest newOriginalReturnSaleRequest) throws OperationException {

        NewOriginalReturnSaleResponse newOriginalReturnSaleResponse;
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
            newOriginalReturnSaleResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewOriginalReturnSaleResponse.class);
            newOriginalReturnSaleResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch(CommonResponseException commonResponseException) {
            newOriginalReturnSaleResponse = new NewOriginalReturnSaleResponse();
            newOriginalReturnSaleResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newOriginalReturnSaleResponse;
    }

    /**
     * Get new sale receipt response
     * @param newSaleRequest                      New Sale Request
     * @return                                    New Sale Response
     * @throws OperationException                 Cash Register Exception
     */
    public NewSaleResponse getNewSaleResponse(
            NewSaleRequest newSaleRequest) throws OperationException {

        NewSaleResponse newSaleResponse;
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
            newSaleResponse = this.objectMapper.readValue(
                    decryptedResponseByteArray, NewSaleResponse.class);
            newSaleResponse.setResponseCode(ResponseCodes.OK.getCode());
        } catch (DecryptionException | EncryptionException |
                IOException | NetworkException exception) {
            throw new OperationException(exception.getMessage());
        } catch (CommonResponseException commonResponseException) {
            newSaleResponse = new NewSaleResponse();
            newSaleResponse.setResponseCode(
                    commonResponseException.getCommonResponse().getResponseCode());
        }
        return newSaleResponse;
    }
}