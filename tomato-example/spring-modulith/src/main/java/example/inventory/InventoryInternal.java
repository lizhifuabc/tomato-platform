package example.inventory;

import org.springframework.stereotype.Component;

/**
 * Some inventory-internal application component. As it is located in the very same package, it can be protected from
 * access by other modules by using the default visibility instead of making it public.
 *
 * @author Oliver Drotbohm
 */
@Component
class InventoryInternal {}
