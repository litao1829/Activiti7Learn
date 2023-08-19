package cn.itcast.hiss.form.exceptions;

/**
 * SqlException
 *
 * @author: wgl
 * @describe: 数据库连接异常
 * @date: 2022/12/28 10:10
 */
public class SqlException extends RuntimeException{
    public SqlException() {
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(Throwable cause) {
        super(cause);
    }

    public SqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
