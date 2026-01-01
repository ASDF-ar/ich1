package com.fast.system.general.exception.file;

import com.fast.system.general.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author fast
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
