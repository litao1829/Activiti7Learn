package cn.itcast.hiss.form.exceptions;

/**
 * StrException
 *
 * @author: wgl
 * @describe: TODO
 * @date: 2022/12/28 10:10
 */
public class StrException extends RuntimeException{
    public StrException() {
    }

    public StrException(String message) {
        super(message);
    }

    public StrException(String message, Throwable cause) {
        super(message, cause);
    }

    public StrException(Throwable cause) {
        super(cause);
    }

    public StrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
