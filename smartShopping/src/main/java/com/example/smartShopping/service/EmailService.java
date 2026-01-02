package com.example.smartShopping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Gửi email xác thực với verification code
     */
    public void sendVerificationEmail(String toEmail, String username, String verificationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Smart Shopping - Email Verification");
            
            String htmlContent = buildVerificationEmailHtml(username, verificationCode);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Verification email sent successfully to: {}", toEmail);
            
        } catch (MessagingException e) {
            log.error("Failed to send verification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    /**
     * Gửi email đơn giản (fallback nếu HTML không hoạt động)
     */
    public void sendSimpleVerificationEmail(String toEmail, String verificationCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Smart Shopping - Email Verification");
            message.setText("Your verification code is: " + verificationCode + 
                          "\n\nThis code will expire in 10 minutes." +
                          "\n\nThank you for registering with Smart Shopping!");
            
            mailSender.send(message);
            log.info("Simple verification email sent successfully to: {}", toEmail);
            
        } catch (Exception e) {
            log.error("Failed to send simple verification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    /**
     * Build HTML content cho verification email
     */
    private String buildVerificationEmailHtml(String username, String verificationCode) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <meta charset='UTF-8'>" +
               "    <style>" +
               "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
               "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
               "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }" +
               "        .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }" +
               "        .code-box { background: white; border: 2px dashed #667eea; padding: 20px; text-align: center; margin: 20px 0; border-radius: 8px; }" +
               "        .code { font-size: 32px; font-weight: bold; color: #667eea; letter-spacing: 5px; }" +
               "        .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }" +
               "        .warning { color: #e74c3c; font-size: 14px; margin-top: 15px; }" +
               "    </style>" +
               "</head>" +
               "<body>" +
               "    <div class='container'>" +
               "        <div class='header'>" +
               "            <h1>🛒 Smart Shopping</h1>" +
               "            <p>Email Verification</p>" +
               "        </div>" +
               "        <div class='content'>" +
               "            <h2>Hello " + (username != null ? username : "User") + "!</h2>" +
               "            <p>Thank you for registering with Smart Shopping. To complete your registration, please use the verification code below:</p>" +
               "            <div class='code-box'>" +
               "                <div class='code'>" + verificationCode + "</div>" +
               "            </div>" +
               "            <p>Enter this code in the app to verify your email address.</p>" +
               "            <p class='warning'>⏰ This code will expire in 10 minutes.</p>" +
               "            <p>If you didn't request this verification, please ignore this email.</p>" +
               "        </div>" +
               "        <div class='footer'>" +
               "            <p>© 2026 Smart Shopping. All rights reserved.</p>" +
               "            <p>This is an automated message, please do not reply.</p>" +
               "        </div>" +
               "    </div>" +
               "</body>" +
               "</html>";
    }

    /**
     * Gửi email reset password với verification code
     */
    public void sendPasswordResetEmail(String toEmail, String username, String resetCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Smart Shopping - Password Reset");
            
            String htmlContent = buildPasswordResetEmailHtml(username, resetCode);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Password reset email sent successfully to: {}", toEmail);
            
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    /**
     * Build HTML content cho password reset email
     */
    private String buildPasswordResetEmailHtml(String username, String resetCode) {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <meta charset='UTF-8'>" +
               "    <style>" +
               "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
               "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
               "        .header { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }" +
               "        .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }" +
               "        .code-box { background: white; border: 2px dashed #f5576c; padding: 20px; text-align: center; margin: 20px 0; border-radius: 8px; }" +
               "        .code { font-size: 32px; font-weight: bold; color: #f5576c; letter-spacing: 5px; }" +
               "        .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }" +
               "        .warning { color: #e74c3c; font-size: 14px; margin-top: 15px; }" +
               "    </style>" +
               "</head>" +
               "<body>" +
               "    <div class='container'>" +
               "        <div class='header'>" +
               "            <h1>🔐 Smart Shopping</h1>" +
               "            <p>Password Reset Request</p>" +
               "        </div>" +
               "        <div class='content'>" +
               "            <h2>Hello " + (username != null ? username : "User") + "!</h2>" +
               "            <p>We received a request to reset your password. Use the code below to reset your password:</p>" +
               "            <div class='code-box'>" +
               "                <div class='code'>" + resetCode + "</div>" +
               "            </div>" +
               "            <p>Enter this code in the app along with your new password.</p>" +
               "            <p class='warning'>⏰ This code will expire in 10 minutes.</p>" +
               "            <p><strong>If you didn't request a password reset, please ignore this email and your password will remain unchanged.</strong></p>" +
               "        </div>" +
               "        <div class='footer'>" +
               "            <p>© 2026 Smart Shopping. All rights reserved.</p>" +
               "            <p>This is an automated message, please do not reply.</p>" +
               "        </div>" +
               "    </div>" +
               "</body>" +
               "</html>";
    }
}
