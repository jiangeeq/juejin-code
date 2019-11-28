package com.jpsite.interesting.controller;

import com.jpsite.interesting.domain.ShortLink;
import com.jpsite.interesting.repository.ShortLinkRepository;
import com.jpsite.interesting.util.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * 生成短链接请求类
 *
 * @author jiangpeng
 * @date 2019/11/2715:19
 */
@Controller
@RequestMapping("shortLink")
public class ShortLinkController {
    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @GetMapping
    public String shortLink() {
        return "short_link";
    }

    /**
     * 生成短链接
     *
     * @param url 要转换的url
     * @return short_link.ftl
     */
    @PostMapping
    public String createShortLink(String url, HttpServletRequest request) throws UnknownHostException {
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        ShortLink shortLink = shortLinkRepository.save(new ShortLink(url, Date.from(instant)));
        String shortStr = ConversionUtils.encode(shortLink.getId(), 4);

        request.setAttribute("url", url);
        request.setAttribute("shortUrl", getServerUrl(request) + "/shortLink/" + shortStr);

        return "short_link";
    }

    /**
     * 解析短链接并跳转页面
     *
     * @param shortUrl 短链接参数
     */
    @RequestMapping("/{shortUrl}")
    public void redirectToSourceUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        long id = ConversionUtils.decode(shortUrl);
        Optional<ShortLink> shortLinkOpt = shortLinkRepository.findById(id);
        String url = shortLinkOpt.orElseGet(null).getUrl();
        response.sendRedirect(url);
    }

    /**
     * 获取当前应用服务器域名和端口
     * @return String
     */
    private String getServerUrl(HttpServletRequest request) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        //获取服务器域名
        String serverName = request.getServerName();
        //获取服务器端口
        int serverPort = request.getServerPort();
        //获取服务器IP地址;
        String hostAddress = InetAddress.getByName(request.getServerName()).getHostAddress();

        return sb.append("http://").append(serverName).append(":").append(serverPort).toString();
    }
}
