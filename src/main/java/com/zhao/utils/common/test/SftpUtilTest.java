package com.zhao.utils.common.test;

import com.jcraft.jsch.ChannelSftp;
import com.zhao.utils.common.util.SftpUtil;

/**
 * 可以自行搭建sftp服务器进行测试
 * 推荐: FreeSSHd
 */
public class SftpUtilTest {
    public static void main(String[] args) {
        //配置信息
        String host = "127.0.0.1";
        String username = "admin";
        String password = "123456";
        int point = 22;

        //配置sftp客户端
        ChannelSftp sftp = SftpUtil.connectSFTP(host,username,password,point);

        String directory = "/";//sftp文件所在目录
        String fileName = "test.txt";//文件名称
        String saveFile ="D:\\1work\\文档\\test1.txt";//保存的文件路径或者是重新命名的文件
        SftpUtil.downloadFile(directory,fileName,saveFile,sftp);
        SftpUtil.disconnected(sftp);
    }
}
