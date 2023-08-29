package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BytebuddyAgentApplication java -javaagent:agentjar文件的位置 [= 传入 premain的参数 ] -jar
 * 要运行的jar文件的位置
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@SpringBootApplication
public class BytebuddyAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BytebuddyAgentApplication.class, args);
	}

}
