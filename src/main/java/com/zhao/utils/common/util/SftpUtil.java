package com.zhao.utils.common.util;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 使用的maven依赖为 :
 * <!-- https://mvnrepository.com/artifact/com.jcraft/jsch -->
 * <dependency>
 * <groupId>com.jcraft</groupId>
 * <artifactId>jsch</artifactId>
 * <version>0.1.55</version>
 * </dependency>
 *
 * 测试可以使用 * FreeSSHd 搭建sftp服务器
 */
public class SftpUtil {

    /**
     * 通过用户名密码连接客户端（已验证可用）
     *
     * @param host     sftp服务器 ip
     * @param username sftp 用户名
     * @param password sftp 密码
     * @param port     sftp 服务端口号，默认为 22
     * @return
     */
    public static ChannelSftp connectSFTP(String host, String username, String password, int port) {

        JSch jsch = new JSch();
        Channel channel = null;
        try {
            Session session = jsch.getSession(username, host, port);
            if (password != null && !"".equals(password)) {
                session.setPassword(password);
            }
            Properties sshConfig = new Properties();
            // StrictHostKeyChecking=no 最不安全的级别，当然也没有那么多烦人的提示了，相对安全的内网测试时建议使用
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            // 超时时间,默认为0。如果网络不好，可以设置此值
            // session.setTimeout(timeout);
            session.setServerAliveInterval(92000);
            session.connect();
            //参数sftp指明要打开的连接是sftp连接
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return (ChannelSftp) channel;
    }

    /**
     * 通过密钥文件连接客户端
     *
     * @param host       sftp服务器 ip
     * @param username   sftp 用户名
     * @param privateKey sftp 密钥文件地址
     * @param passphrase sftp 密钥口令
     * @param port       sftp 服务端口号，默认为 22
     * @return
     */
    public static ChannelSftp connectSFTP(String host, String username, String privateKey, String passphrase, int port) {
        JSch jsch = new JSch();
        Channel channel = null;
        try {
            if (privateKey != null && !"".equals(privateKey)) {
                //使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
                if (passphrase != null && "".equals(passphrase)) {
                    jsch.addIdentity(privateKey, passphrase);
                } else {
                    jsch.addIdentity(privateKey);
                }
            }
            Session session = jsch.getSession(username, host, port);
            Properties sshConfig = new Properties();
            // StrictHostKeyChecking=no 最不安全的级别，当然也没有那么多烦人的提示了，相对安全的内网测试时建议使用
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            //设置超时时间，默认为0
            //session.setTimeout(timeout);
            //设置多久和服务器做一次确认
            session.setServerAliveInterval(92000);
            session.connect();
            //参数sftp指明要打开的连接是sftp连接
            channel = session.openChannel("sftp");
            //进行sftp服务连接
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        //返回设置好的sftp客户端
        return (ChannelSftp) channel;
    }



    /**
     * 下载文件
     * @param directory sftp文件的目录
     * @param downloadFile sftp上的文件名称
     * @param saveFile 要保存的文件的路径
     * @param sftp 当然是sftp客户端了，用于切换不同的sftp服务器。
     */
    public static void downloadFile(String directory, String downloadFile,
                         String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param directory 上传文件的目录
     * @param uploadFile 上传文件的名称
     * @param sftp sftp客户端
     */
    public static void uploadFile(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param directory 删除文件的目录
     * @param deleteFile 删除文件名称
     * @param sftp
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开客户端，释放资源
     * @param sftp
     */
    public static void disconnected(ChannelSftp sftp) {
        if (sftp != null) {
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                e.printStackTrace();
            }
            sftp.disconnect();
        }
    }
}
