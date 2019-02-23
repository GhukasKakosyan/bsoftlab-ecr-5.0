package BSOFTLABECR.client;

import BSOFTLABECR.exception.ECRException;

import BSOFTLABECR.request.cashier.CashiersAndDepsRequest;
import BSOFTLABECR.request.cashier.LoginCashierRequest;
import BSOFTLABECR.request.cashier.LogoutCashierRequest;
import BSOFTLABECR.request.headerfooter.HeaderFooterRequest;
import BSOFTLABECR.request.receipt.prepayment.ExistPrepaymentRequest;
import BSOFTLABECR.request.receipt.prepayment.NewPrepaymentRequest;
import BSOFTLABECR.request.receipt.returns.prepayment.ExistRPrepaymentRequest;
import BSOFTLABECR.request.receipt.returns.prepayment.NewORPrepaymentRequest;
import BSOFTLABECR.request.receipt.returns.prepayment.NewPRPrepaymentRequest;
import BSOFTLABECR.request.receipt.returns.sale.ExistRSaleRequest;
import BSOFTLABECR.request.receipt.returns.sale.NewORSaleRequest;
import BSOFTLABECR.request.receipt.returns.sale.NewPRSaleRequest;
import BSOFTLABECR.request.receipt.sale.ExistSaleRequest;
import BSOFTLABECR.request.receipt.sale.NewSaleRequest;
import BSOFTLABECR.request.report.FiscalReportRequest;

import BSOFTLABECR.response.cashier.CashiersAndDepsResponse;
import BSOFTLABECR.response.cashier.LoginCashierResponse;
import BSOFTLABECR.response.cashier.LogoutCashierResponse;
import BSOFTLABECR.response.headerfooter.HeaderFooterResponse;
import BSOFTLABECR.response.receipt.prepayment.ExistPrepaymentResponse;
import BSOFTLABECR.response.receipt.prepayment.NewPrepaymentResponse;
import BSOFTLABECR.response.receipt.returns.prepayment.ExistRPrepaymentResponse;
import BSOFTLABECR.response.receipt.returns.prepayment.NewORPrepaymentResponse;
import BSOFTLABECR.response.receipt.returns.prepayment.NewPRPrepaymentResponse;
import BSOFTLABECR.response.receipt.returns.sale.ExistRSaleResponse;
import BSOFTLABECR.response.receipt.returns.sale.NewORSaleResponse;
import BSOFTLABECR.response.receipt.returns.sale.NewPRSaleResponse;
import BSOFTLABECR.response.receipt.sale.ExistSaleResponse;
import BSOFTLABECR.response.receipt.sale.NewSaleResponse;
import BSOFTLABECR.response.report.FiscalReportResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
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

public class ECRClient {

    private static final String TRIPLE_DES_ALG = "DESede";
    private static final String CIPHER_ALGORITHM_SPEC = TRIPLE_DES_ALG + "/ECB/PKCS7Padding";
    private static final byte[] PROTOCOL_VERSION = new byte[]{(byte)0x00, (byte)0x05};
    private static final byte[] REQUEST_HEADER = new byte[]{
            (byte)0xD5, (byte)0x80, (byte)0xD4, (byte)0xB4, (byte)0xD5, (byte)0x84,
            PROTOCOL_VERSION[0], PROTOCOL_VERSION[1]
    };
    static {Security.addProvider(new BouncyCastleProvider());}

    private static final short REQUEST_HEADER_OP_LENGTH = 2;
    private static final short REQUEST_HEADER_PAYLOAD_LENGHT = 2;
    private static final short REQUEST_HEADER_LENGTH = (short)(REQUEST_HEADER.length + REQUEST_HEADER_OP_LENGTH + REQUEST_HEADER_PAYLOAD_LENGHT);

    private static final byte ZERO = 0;
    private static final byte OPER_CASHIERS_AND_DEPS_LIST = 1;
    private static final byte OPER_CASHIER_LOGIN = 2;
    private static final byte OPER_CASHIER_LOGOUT = 3;
    private static final byte OPER_NEW_SALE_PREPAYMENT_RECEIPT = 4;
    private static final byte OPER_NEW_RETURN_RECEIPT = 6;
    private static final byte OPER_SETUP_HEADERFOOTER = 7;
    private static final byte OPER_PRINT_FISCAL_REPORT = 9;
    private static final byte OPER_GET_EXISTED_RECEIPT = 10;

    private int seq = 0;
    private byte[] passwordKey;
    private byte[] sessionKey;
    private ObjectMapper objectMapper;
    private SocketChannel socketChannel;

    public byte[] getPasswordKey() {return this.passwordKey;}
    public int getSeq() {
        return ++this.seq;
    }

    public byte[] getSessionKey() {return this.sessionKey;}
    public void setSessionKey (byte[] sessionKey) {this.sessionKey = sessionKey;}

    /**
     * @throws NoSuchAlgorithmException If the "3DES/ECB/PKCS7Padding" security provider is not registered
     */
    public ECRClient(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        this.passwordKey = Arrays.copyOf(messageDigest.digest(password.getBytes()), 24);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public void connect(String ip, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.connect(new InetSocketAddress(ip, port));
    }

    public void disconnect() throws IOException {
        this.socketChannel.shutdownInput();
        this.socketChannel.shutdownOutput();
        this.socketChannel.close();
    }

    /**
     * Encrypts byte array using password key or session key
     * @param usePasswordKey                                        Use password key (true) or session key (false)
     * @param byteArray                                             Byte Array to be encrypted
     * @return                                                      Encrypted byte array using password key or session key
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    private byte[] getEncryptedByteArray(boolean usePasswordKey, byte[] byteArray)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        byte[] encryptKey = usePasswordKey ? this.passwordKey : this.sessionKey;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey, TRIPLE_DES_ALG));
        byte[] encryptedByteArray;
        encryptedByteArray = cipher.doFinal(byteArray);
		return encryptedByteArray;
    }

    /**
     * Decrypts byte array using password key or session key
     * @param usePasswordKey                                        Use password key (true) or session key (false)
     * @param encryptedByteArray                                    Byte array to be decrypted
     * @return                                                      decrypted byte array using password or session key
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    private byte[] getDecryptedByteArray(boolean usePasswordKey, byte[] encryptedByteArray)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        byte[] decryptKey = usePasswordKey ? this.passwordKey : this.sessionKey;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_SPEC);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey, TRIPLE_DES_ALG));
        byte[] decryptedByteArray;
        decryptedByteArray = cipher.doFinal(encryptedByteArray);
        return decryptedByteArray;
    }

    /**
     * Send encrypted ByteBuffer of request and receive encrypted ByteBuffer of response
     * @param encryptedRequestByteBuffer encrypted ByteBuffer of request to be sent
     * @return encrypted ByteBuffer of response to be received
     * @throws java.io.IOException Network error
     */
    private ByteBuffer sendRequestReceiveResponse(ByteBuffer encryptedRequestByteBuffer)
            throws IOException, ECRException {

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

        int responseCode = headerEncryptedResponseByteBuffer.getShort() & 0xFFFF; // Get two bytes of response code as integer
        if (ResponseCodes.get(responseCode) != ResponseCodes.OK)
            throw new AssertionError(responseCode); // Generate AssertionError type Exception if not OK

        int length = headerEncryptedResponseByteBuffer.getShort() & 0xFFFF; // Get two bytes of response length as integer
        headerEncryptedResponseByteBuffer.get(); // Get first byte of two reserved bytes
        headerEncryptedResponseByteBuffer.get(); // Get second byte of two reserved bytes

        ByteBuffer encryptedResponseByteBuffer = ByteBuffer.allocate(length); // Prepare ByteBuffer for body of encrypted response
        this.socketChannel.read(encryptedResponseByteBuffer); // Reads body of encrypted response
        return encryptedResponseByteBuffer;
    }
    /**
     * Get cashiers and departments list
     * @param cashiersAndDepsRequest                                Cashiers and departments list request
     * @return                                                      cashiers and departments list
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public CashiersAndDepsResponse getCashiersAndDepsResponse(CashiersAndDepsRequest cashiersAndDepsRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(cashiersAndDepsRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(true, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_CASHIERS_AND_DEPS_LIST).put(ZERO).putShort(length).put(encryptedRequestBytes);
        CashiersAndDepsResponse cashiersAndDepsResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(true, encryptedResponseBytes);
            cashiersAndDepsResponse = this.objectMapper.readValue(decryptedResponseBytes, CashiersAndDepsResponse.class);
            cashiersAndDepsResponse.setResponseCode(200);
        } catch(AssertionError assertionError){
            cashiersAndDepsResponse = new CashiersAndDepsResponse();
            cashiersAndDepsResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return cashiersAndDepsResponse;
    }

    /**
     * Logs in given cashier and returns cashier response with session key
     * @param loginCashierRequest                                   Cashier login request
     * @return                                                      Cashier login response with session key
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public LoginCashierResponse getLoginCashierResponse(LoginCashierRequest loginCashierRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(loginCashierRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(true, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_CASHIER_LOGIN).put(ZERO).putShort(length).put(encryptedRequestBytes);
        LoginCashierResponse loginCashierResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(true, encryptedResponseBytes);
            loginCashierResponse = this.objectMapper.readValue(decryptedResponseBytes, LoginCashierResponse.class);
            loginCashierResponse.setResponseCode(200);
        } catch(AssertionError assertionError){
            loginCashierResponse = new LoginCashierResponse();
            loginCashierResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return loginCashierResponse;
    }

    /**
     * Logout current cashier
     * @param logoutCashierRequest                                  Logout cashier request
     * @return                                                      Logout cashier response
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public LogoutCashierResponse getLogoutCashierResponse(LogoutCashierRequest logoutCashierRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(logoutCashierRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        LogoutCashierResponse logoutCashierResponse = new LogoutCashierResponse();
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_CASHIER_LOGOUT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        try {
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            this.sessionKey = null;
            logoutCashierResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            logoutCashierResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return logoutCashierResponse;
    }

    /**
     * Setup header and footer for receipt
     * @param headerFooterRequest                                   Setup headers and footers request
     * @return                                                      returns response code
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public HeaderFooterResponse setupHeaderFooter(HeaderFooterRequest headerFooterRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(headerFooterRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        HeaderFooterResponse headerFooterResponse = new HeaderFooterResponse();
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_SETUP_HEADERFOOTER).put(ZERO).putShort(length).put(encryptedRequestBytes);
        try {
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            headerFooterResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            headerFooterResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return headerFooterResponse;
    }

    /**
     * Prints fiscal report for. Parameters of report are set in fiscalReportRequest object.
     * @param fiscalReportRequest                                   Fiscal Report Request
     * @return                                                      returns response code
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     **/
    public FiscalReportResponse printFiscalReport(FiscalReportRequest fiscalReportRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(fiscalReportRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        FiscalReportResponse fiscalReportResponse = new FiscalReportResponse();
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_PRINT_FISCAL_REPORT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        try {
            this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            fiscalReportResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            fiscalReportResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return fiscalReportResponse;
    }

    /**
     * Get existed prepayment receipt response
     * @param existPrepaymentRequest receipt that we want to get
     * @return Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public ExistPrepaymentResponse getExistPrepaymentResponse(ExistPrepaymentRequest existPrepaymentRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(existPrepaymentRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_GET_EXISTED_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        ExistPrepaymentResponse existPrepaymentResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            existPrepaymentResponse = this.objectMapper.readValue(decryptedResponseBytes, ExistPrepaymentResponse.class);
            existPrepaymentResponse.setResponseCode(200);
        } catch (AssertionError assertionError) {
            existPrepaymentResponse = new ExistPrepaymentResponse();
            existPrepaymentResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return existPrepaymentResponse;
    }

    /**
     * Get existed return prepayment receipt response
     * @param existRPrepaymentRequest receipt that we want to get
     * @return Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public ExistRPrepaymentResponse getExistRPrepaymentResponse(ExistRPrepaymentRequest existRPrepaymentRequest)
            throws BadPaddingException, ECRException, IOException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(existRPrepaymentRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_GET_EXISTED_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        ExistRPrepaymentResponse existRPrepaymentResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            existRPrepaymentResponse = this.objectMapper.readValue(decryptedResponseBytes, ExistRPrepaymentResponse.class);
            existRPrepaymentResponse.setResponseCode(200);
        } catch (AssertionError assertionError) {
            existRPrepaymentResponse = new ExistRPrepaymentResponse();
            existRPrepaymentResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return existRPrepaymentResponse;
    }

    /**
     * Get existed return sale receipt response
     * @param existRSaleRequest receipt that we want to get
     * @return Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public ExistRSaleResponse getExistRSaleResponse(ExistRSaleRequest existRSaleRequest)
            throws BadPaddingException, ECRException, IOException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(existRSaleRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_GET_EXISTED_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        ExistRSaleResponse existRSaleResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            existRSaleResponse = this.objectMapper.readValue(decryptedResponseBytes, ExistRSaleResponse.class);
            existRSaleResponse.setResponseCode(200);
        } catch (AssertionError assertionError) {
            existRSaleResponse = new ExistRSaleResponse();
            existRSaleResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return existRSaleResponse;
    }

    /**
     * Get existed sale receipt response
     * @param existSaleRequest receipt that we want to get
     * @return Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                   ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException   @request cannot be serialized to JSON
     * @throws java.io.IOException                                  Standart Input Output Exception
     * @throws java.security.InvalidKeyException                    3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException               3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                     Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException               3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                  Invalid Padding size
     */
    public ExistSaleResponse getExistSaleResponse(ExistSaleRequest existSaleRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(existSaleRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_GET_EXISTED_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        ExistSaleResponse existSaleResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            existSaleResponse = this.objectMapper.readValue(decryptedResponseBytes, ExistSaleResponse.class);
            existSaleResponse.setResponseCode(200);
        } catch (AssertionError assertionError) {
            existSaleResponse = new ExistSaleResponse();
            existSaleResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return existSaleResponse;
    }

    /**
     * Get new prepayment receipt response
     * @param newPrepaymentRequest prepayment receipt request related information
     * @return Prepayment Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewPrepaymentResponse getNewPrepaymentResponse(NewPrepaymentRequest newPrepaymentRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newPrepaymentRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_SALE_PREPAYMENT_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewPrepaymentResponse newPrepaymentResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newPrepaymentResponse = this.objectMapper.readValue(decryptedResponseBytes, NewPrepaymentResponse.class);
            newPrepaymentResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            newPrepaymentResponse = new NewPrepaymentResponse();
            newPrepaymentResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newPrepaymentResponse;
    }

    /**
     * Get new partial return prepayment receipt response
     * @param newPRPrepaymentRequest return prepayment receipt request related information
     * @return Return prepayment receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewPRPrepaymentResponse getNewPRPrepaymentResponse(NewPRPrepaymentRequest newPRPrepaymentRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newPRPrepaymentRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_RETURN_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewPRPrepaymentResponse newPRPrepaymentResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newPRPrepaymentResponse = this.objectMapper.readValue(decryptedResponseBytes, NewPRPrepaymentResponse.class);
            newPRPrepaymentResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            newPRPrepaymentResponse = new NewPRPrepaymentResponse();
            newPRPrepaymentResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newPRPrepaymentResponse;
    }

    /**
     * Get new original return prepayment receipt response
     * @param newORPrepaymentRequest return prepayment receipt request related information
     * @return Return prepayment receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewORPrepaymentResponse getNewORPrepaymentResponse(NewORPrepaymentRequest newORPrepaymentRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newORPrepaymentRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_RETURN_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewORPrepaymentResponse newORPrepaymentResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newORPrepaymentResponse = this.objectMapper.readValue(decryptedResponseBytes, NewORPrepaymentResponse.class);
            newORPrepaymentResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            newORPrepaymentResponse = new NewORPrepaymentResponse();
            newORPrepaymentResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newORPrepaymentResponse;
    }

    /**
     * Get new partial return sale receipt response
     * @param newPRSaleRequest return sale receipt request related information
     * @return Return sale receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewPRSaleResponse getNewPRSaleResponse(NewPRSaleRequest newPRSaleRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newPRSaleRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_RETURN_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewPRSaleResponse newPRSaleResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newPRSaleResponse = this.objectMapper.readValue(decryptedResponseBytes, NewPRSaleResponse.class);
            newPRSaleResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            newPRSaleResponse = new NewPRSaleResponse();
            newPRSaleResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newPRSaleResponse;
    }

    /**
     * Get new original return sale receipt response
     * @param newORSaleRequest return sale receipt request related information
     * @return Return sale receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewORSaleResponse getNewORSaleResponse(NewORSaleRequest newORSaleRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newORSaleRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_RETURN_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewORSaleResponse newORSaleResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newORSaleResponse = this.objectMapper.readValue(decryptedResponseBytes, NewORSaleResponse.class);
            newORSaleResponse.setResponseCode(200);
        } catch(AssertionError assertionError) {
            newORSaleResponse = new NewORSaleResponse();
            newORSaleResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newORSaleResponse;
    }

    /**
     * Get new sale receipt response
     * @return Sale Receipt related information or error status
     * @throws BSOFTLABECR.exception.ECRException                 ECR Exception
     * @throws com.fasterxml.jackson.core.JsonProcessingException request cannot be serialized to JSON
     * @throws java.io.IOException                                Standart Input Output Exception
     * @throws java.security.InvalidKeyException                  3DES encryption/decryption key is not correct
     * @throws java.security.NoSuchAlgorithmException             3DES encryption/decryption algorithm is not present
     * @throws javax.crypto.BadPaddingException                   Invalid 3DES Padding block
     * @throws javax.crypto.IllegalBlockSizeException             3DES Padding block not correct
     * @throws javax.crypto.NoSuchPaddingException                Invalid Padding size
     */
    public NewSaleResponse getNewSaleResponse(NewSaleRequest newSaleRequest)
            throws BadPaddingException, ECRException, IOException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] requestBytes = this.objectMapper.writeValueAsBytes(newSaleRequest);
        byte[] encryptedRequestBytes = this.getEncryptedByteArray(false, requestBytes);
        short length = (short)encryptedRequestBytes.length;
        ByteBuffer encryptedRequestByteBuffer = ByteBuffer.allocate(REQUEST_HEADER_LENGTH + length);
        encryptedRequestByteBuffer.put(REQUEST_HEADER).put(OPER_NEW_SALE_PREPAYMENT_RECEIPT).put(ZERO).putShort(length).put(encryptedRequestBytes);
        NewSaleResponse newSaleResponse;
        try {
            ByteBuffer encryptedResponseByteBuffer;
            encryptedResponseByteBuffer = this.sendRequestReceiveResponse(encryptedRequestByteBuffer);
            byte[] encryptedResponseBytes = encryptedResponseByteBuffer.array();
            byte[] decryptedResponseBytes;
            decryptedResponseBytes = this.getDecryptedByteArray(false, encryptedResponseBytes);
            newSaleResponse = this.objectMapper.readValue(decryptedResponseBytes, NewSaleResponse.class);
            newSaleResponse.setResponseCode(200);
        } catch (AssertionError assertionError) {
            newSaleResponse = new NewSaleResponse();
            newSaleResponse.setResponseCode(Integer.parseInt(assertionError.getMessage()));
        }
        return newSaleResponse;
    }

    enum ResponseCodes {

        OK(200),
        INTERNAL_ERROR(500),

        ERROR_BAD_REQUEST(400),
        ERROR_WRONG_PROTOCOL_VERSION(402),

        ERROR_UNAUTHORIZED_CONNECTION(403),
        ERROR_BAD_OP_CODE(404),

        SIMPLE_RECEIPT_NOT_ALLOWED(410),

        LOGIN_ERROR_WRONG_PASS(101),
        ERROR_WRONG_SESSION(102),
        ERROR_WRONG_REQ_HEADER(103),
        ERROR_WRONG_RSEQ(104),
        ERROR_WRONG_JSON(105),

        LOGIN_ERROR_WRONG_USER(111),
        LOGIN_ERROR_NO_SUCH_USER(112),
        LOGIN_ERROR_INACTIVE_USER(113),

        ERROR_NO_USER(121),

        LAST_RECEIPT_NO_FILE(141),
        LAST_RECEIPT_ANOTHER_CASHIER(142),
        LAST_RECEIPT_PRN_FAIL(143),
        LAST_RECEIPT_PRN_INIT(144),
        LAST_RECEIPT_PRN_NO_PAPER(145),

        PRINT_RECEIPT_NO_DEPT(151),
        PRINT_RECEIPT_PAID_LESS(152),
        PRINT_RECEIPT_LIMIT(153),
        PRINT_RECEIPT_AMOUNT_POS(154),
        PRINT_RECEIPT_SYNC_NEEDED(155),
        PRINT_RECEIPT_SIMPLE_INVALID_ITEM_COUNT(156),
        RECEIPT_RETURN_INVALID_ID(157),
        RECEIPT_RETURN_ALREADY_RETURNED(158),
        RECEIPT_PRICE_QTY_NONZERO(159),
        RECEIPT_PRICE_INVALID_DISCOUNT_PERCENT(160),

        PRINT_RECEIPT_ERROR_EMPTY_CODE(161),
        PRINT_RECEIPT_ERROR_EMPTY_NAME(162),

        PRINT_RECEIPT_AMOUNT_CASH(163),
        PAYMENT_FAILED(164),
        RECEIPT_ITEM_FULL_PRICE_NONZERO(165),
        RECEIPT_PRICE_QTY_NOT_EQUAL(166),
        RECEIPT_PAYMENT_AMOUNT_MORE_THAN_TOTAL(167),
        RECEIPT_PAYMENT_AMOUNT_REDUNDANT_CASH(168),

        PRINT_FISCAL_REPORT_WRONG_FILTERS(169),
        PRINT_FISCAL_REPORT_INVALID_PERIOD(170),

        RECEIPT_PRICE_INVALID_ITEM_TOTAL_PRICE(171),
        GET_RECEIPT_TYPE_NOT_A_PRODUCT_RECEIPT(172),
        INVALID_DISCOUNT_TYPES(173),
        RETURN_RECEIPT_DOES_NOT_EXIST(174),

        RETURN_RECEIPT_INVALID_CRN(175),
        ERROR_LAST_RECEIPT_DOES_NOT_EXIST(176),
        RETURN_RECEIPT_TYPE_NOT_SUPPORTED(177),
        RECEIPT_RETURN_BAD_REQUESTED_AMOUNT(178),
        ERROR_PARTIAL_PAYMENT_RECEIPT_MUST_BE_RETURNED_FULLY(179),
        RECEIPT_RETURN_MISSING_PRODUCT_LIST(180),
        RECEIPT_RETURN_INVALID_QUANTITY_FOR_ITEM(181),
        ERROR_RETURN_RECEIPT_IS_RETURN_TYPE(182),
        ERROR_INVALID_ATG_CODE(183),
        PREPAYMENT_RETURN_INVALID_REQUEST(184),
        RECEIPT_HAS_NOT_SYNCED_REFERENCED_RECEIPTS(185),

        UNKNOWN_RESPONSE_CODE(-1);

        int code;

        ResponseCodes(int code) {
            this.code = code;
        }
        public static ResponseCodes get(int code) {
            for (ResponseCodes a : ResponseCodes.values()) {
                if (a.code == code)
                    return a;
            }
            return UNKNOWN_RESPONSE_CODE;
        }
    }
}