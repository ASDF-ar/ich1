package com.fast.system.general.exception.user;

import com.fast.system.general.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author fast
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
