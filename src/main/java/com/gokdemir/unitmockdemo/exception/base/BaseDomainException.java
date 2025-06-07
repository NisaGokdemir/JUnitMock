package com.gokdemir.unitmockdemo.exception.base;

public class BaseDomainException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public BaseDomainException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}

// Birden fazla exception türü tanımlanabilir.
// Custom exception'lar, uygulamanın iş mantığına özgü hataları temsil eder ve genellikle `BaseDomainException` sınıfından türetilir.
// Ancak diğer türlerini de duruma göre kullanabiliriz.
// Bazı yaygın exception türleri ve açıklamaları:

//BusinessException	        İş kurallarına aykırı durumlar
//ValidationException	    DTO validasyonları başarısızsa
//EntityNotFoundException	DB'de kayıt bulunamazsa (genelde Business'tan türetilir)
//AuthenticationException	Kimlik doğrulama başarısızsa	(Spring Security'de var)
//AuthorizationException	Yetkisiz erişim durumlarında	(Yetki kontrolü yoksa)
//InternalException	        Bilinmeyen sistem hataları	(Try-catch’le sarmalanabilir)

//Türlere göre alt servislerde kendi çatıları ve bu çatılara özel error kodları tanımlanabilir.
