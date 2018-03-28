RSA 前端加密

1.在后台通过程序生成公钥串

2.前台引用加密JS

3.

// java后台生成的
var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTu5u08Wel08uWM02GYYRlFXfmEHH7DAbSWufUdU8NlZrE/4BAOwqPMu/vMLdCi4GRHDSmWhoqjC5/7oKoALl6nFCAObtSl6RiWdc8KvcN1D45PASs8M/YPY+oa8iNYZA/drtgXEw4NniC0EB47miGVL4POyLOE3dJlk4LD/AjtQIDAQAB";
var encrypt = new JSEncrypt();
encrypt.setPublicKey(publicKey);
// 加密
alert(encrypt.encrypt("hello"));