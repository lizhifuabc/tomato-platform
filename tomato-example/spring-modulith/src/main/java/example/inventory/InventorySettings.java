package example.inventory;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Some Spring Boot configuration properties exposed by the inventory.
 *
 * @author Oliver Drotbohm
 */
@ConfigurationProperties("example.inventory")
record InventorySettings(int stockThreshold) {

	/**
	 * Some Javadoc.
	 * @return
	 */
	public int stockThreshold() {
		return stockThreshold;
	}
}
