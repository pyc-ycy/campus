package com.pyc.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 彭友聪
 */
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
public class CampusApplication {


    public static void main(String[] args) {
        SpringApplication.run(CampusApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    Campus启动成功      ヾ(◍°∇°◍)ﾉﾞ\n"
                +"\n" +
                "                                                                                                                      \n" +
                "                                                                                                                      \n" +
                "        CCCCCCCCCCCCC                                                                                                 \n" +
                "     CCC::::::::::::C                                                                                                 \n" +
                "   CC:::::::::::::::C                                                                                                 \n" +
                "  C:::::CCCCCCCC::::C                                                                                                 \n" +
                " C:::::C       CCCCCC  aaaaaaaaaaaaa      mmmmmmm    mmmmmmm   ppppp   ppppppppp   uuuuuu    uuuuuu      ssssssssss   \n" +
                "C:::::C                a::::::::::::a   mm:::::::m  m:::::::mm p::::ppp:::::::::p  u::::u    u::::u    ss::::::::::s  \n" +
                "C:::::C                aaaaaaaaa:::::a m::::::::::mm::::::::::mp:::::::::::::::::p u::::u    u::::u  ss:::::::::::::s \n" +
                "C:::::C                         a::::a m::::::::::::::::::::::mpp::::::ppppp::::::pu::::u    u::::u  s::::::ssss:::::s\n" +
                "C:::::C                  aaaaaaa:::::a m:::::mmm::::::mmm:::::m p:::::p     p:::::pu::::u    u::::u   s:::::s  ssssss \n" +
                "C:::::C                aa::::::::::::a m::::m   m::::m   m::::m p:::::p     p:::::pu::::u    u::::u     s::::::s      \n" +
                "C:::::C               a::::aaaa::::::a m::::m   m::::m   m::::m p:::::p     p:::::pu::::u    u::::u        s::::::s   \n" +
                " C:::::C       CCCCCCa::::a    a:::::a m::::m   m::::m   m::::m p:::::p    p::::::pu:::::uuuu:::::u  ssssss   s:::::s \n" +
                "  C:::::CCCCCCCC::::Ca::::a    a:::::a m::::m   m::::m   m::::m p:::::ppppp:::::::pu:::::::::::::::uus:::::ssss::::::s\n" +
                "   CC:::::::::::::::Ca:::::aaaa::::::a m::::m   m::::m   m::::m p::::::::::::::::p  u:::::::::::::::us::::::::::::::s \n" +
                "     CCC::::::::::::C a::::::::::aa:::am::::m   m::::m   m::::m p::::::::::::::pp    uu::::::::uu:::u s:::::::::::ss  \n" +
                "        CCCCCCCCCCCCC  aaaaaaaaaa  aaaammmmmm   mmmmmm   mmmmmm p::::::pppppppp        uuuuuuuu  uuuu  sssssssssss    \n" +
                "                                                                p:::::p                                               \n" +
                "                                                                p:::::p                                               \n" +
                "                                                               p:::::::p                                              \n" +
                "                                                               p:::::::p                                              \n" +
                "                                                               p:::::::p                                              \n" +
                "                                                               ppppppppp                                              \n" +
                "                                                                                                                      \n");
    }

}
