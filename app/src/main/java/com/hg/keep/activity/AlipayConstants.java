package com.hg.keep.activity;

/**
 * Created by sfm on 2017-10-28.
 */

public class AlipayConstants {
    //开发者公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnwlpMHGd9yjoi5ulp7zNHhnWSVXGDuTYvr4PRDx7yQ4L+ckfJlR+cTtTeXOSHdgbPfiBZBs9rgRL8y8IpQDV5ThKW+qnzfyRE/kK25vJrv35LkkgwaYYd+aF/ztDU7d0bptP/GitCQHrYpp8vfmFtcQP6u51hnqVBF52ZulqNmydypIV+pSrTLtsn/0ysYbohHC3qtoPjJy7Bh/hwra36rFoToTICtiqzZV0WlRHW+ldOKCPLyVitq1gELS5iI/CLfQb5BSKmggqQa5Gj/5Fy94HD7bofAWgZA3v2ZGcix+EHnkG4A0BykaIOOUPQ/xTaiMuaY30tOnkQofCuZmnhQIDAQAB";

    //开发者私钥
    public static String APP_PRIVATE_KEY = "MIIEpAIBAAKCAQEAm0H+Q9DtD2mVLk5+NXxNGK7hGpH/Rx/9zP7lYMX/VWKrk2BbREGm44NQb25klEKYzRwdZuBhIMVXPAfprAVA9V6vYmiL4WWxlmkuATBM/oQOsLnq15jvPNdre0mbT0N0GTxmg4eEH8YMfshrvTlMh3QyoAvfzjSSMl/19ZPoD08ilb4NcrjqYAphZWfQwsS0pIDURnSOja5MjIvi7pa5qhtXeTKQTQlvvUj0PcIme70tweyLO7AxLR59uvH6YXvT6KR0dF2EJ26udrwoFk14t0VVTYAAGZ0qj+YotgWGVSCcMnAwBArhphxghHrgli8qlNBGhTMco8lrzYK+qucrXwIDAQABAoIBADzg+bTZ3brnacnImZCbdxewqy/iA7l9/+U8JfF2chKnKCQX6nGvVKNtwEKRhPrlykjTN2w5m5xgBT7S03vSa5BLWnY6DG+LksEp6RIqQGaknaYKoN19aIHw7oJfcHj6ODIVDxWQM+zJmei06ho+t5V9qMQ9I7sVviXmnul2383wW2rTRD5lWw+4WE8RFFUiGApdpN+8OfZJkN287VwjCtf2aBuTEZEZwrMllkZ6CUMzA8e4o39TOSrgHe41cKoDRMstSTMVHH4JrG3pKhzXMcnbe0239rbyXT0LWauVbqTKUm1QqV9EIXrF7gUULPzU973zOKn7jlqS5mhZCe+InOECgYEAzo2Z8SXX27kdIlMh2Wc+SVAyd33oj9SP9gHvxlwXDJ0gJGE8xbqKeoe38E6Rifa0sbyemMDNm0dPKLh+ZxDybrmIk5t8fgLrSPaeWSSkj4MYbxoTWgPvmVDNfbYJnXnFmZa7MCAkmwY+tMa5SwOvxEl39g3MtmqFqCSEcNiUAukCgYEAwGzPEL926LThF7U8TyjGbClaixy7y4iwSa16eCvEmjFelBUehiHu/bAOAK+mZzEDE0tVDD3kMcesI4QKqxbMZAYlGWgv8jUbqzzWinxqYk9HZ/jbHxy9fA2zFvUTI24vC9tAvN4WNHcHNDVvoyZzo4z20kcF4qp/qkjoIEUt/wcCgYBykudedZBsKB26CYUQNA2/2wVzdNsI1W7Zli9rx29dptbKd0mfoJ7p4tisKtjeP4Pxx1/t+ZzTUlXdj6FGCIeB1dalki0XNQVQXsRtTD8gACcQkdVELES+tayW2+AvgvmR/aNtyZaki21DF6x2qN/ZsKsjSHpSgXoMT91LP0ey6QKBgQCQhOgbdwFSkw7/ZFGwTjqAX6quEl2E5tsn6s+xU4XsHKog45+yM+gvxjsuDr4WgHwD0K9Ga3FoZcOGRRRSHORTNz5Dz7k62uI2AJJNHlY0EcDaeM/xlP9HHW7lDhlaodVZvUjo0I+yxba2Ym+/BBETw7pYgZkM/Oq3NNyJ6As8nwKBgQCVmUdwC1+X9IkeWc90DvrlybMahx2kqjxvJY0Q3InRA1QLHgU8U69Q5OMt/xLdIWFbSyjcHibiGDUp+YwsMdwePU60wSlG7CthXXZ8IszJWtAhvPpQDiS5oY2fIsePVyYNxOwfGfaC87VmdC8Nz95cmwksx3EINEt8u36Vwrg5xw==";

    //应用ID
    public static String APP_ID = "2017051907286659";//2017030105972772

    //合作伙伴ID：partnerID
    public static String PID = "2088621705399840";//2088021396543294

    //签名类型，支持RSA2（推荐！）、RSA
    public static String SIGN_TYPE = "RSA2";

    public static String CHARSET = "utf-8";

//    private String getPrivateKey() throws InvalidKeySpecException, NoSuchProviderException, NoSuchAlgorithmException {
//        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(Base64.decode(APP_PRIVATE_KEY));
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
//        PrivateKey privateKey = keyFactory.generatePrivate(privSpec);
//        java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
//        signature.initSign(priKey);
//        signature.update(content.getBytes(DEFAULT_CHARSET));
//        byte[] signed = signature.sign();
//        return Base64.encode(signed);
//    }

}
