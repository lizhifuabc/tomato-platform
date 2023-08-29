// package example.order;
//
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import org.junit.jupiter.api.Test;
// import org.springframework.context.annotation.Import;
// import org.springframework.modulith.ApplicationModuleListener;
// import org.springframework.modulith.events.EventPublicationRegistry;
// import org.springframework.modulith.test.ApplicationModuleTest;
// import org.springframework.modulith.test.Scenario;
// import org.springframework.test.annotation.DirtiesContext;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
/// **
// * A show case for how the Spring Modulith application event publication registry keeps
// track of incomplete publications
// * for failing transactional event listeners
// *
// * @author Oliver Drotbohm
// */
// @ApplicationModuleTest
// @Import(EventPublicationRegistryTests.FailingAsyncTransactionalEventListener.class)
// @DirtiesContext
// @RequiredArgsConstructor
// class EventPublicationRegistryTests {
//
// private final OrderManagement orders;
// private final EventPublicationRegistry registry;
// private final FailingAsyncTransactionalEventListener listener;
//
// @Test
// void leavesPublicationIncompleteForFailingListener(Scenario scenario) throws Exception
// {
//
// var order = new Order();
//
// scenario.stimulate(() -> orders.complete(order))
// .andWaitForStateChange(() -> listener.getEx())
// .andVerify(__ -> {
// assertThat(registry.findIncompletePublications()).hasSize(1);
// });
// }
//
// static class FailingAsyncTransactionalEventListener {
//
// @Getter Exception ex;
//
// @ApplicationModuleListener
// void foo(OrderCompleted event) {
//
// var exception = new IllegalStateException("¯\\_(ツ)_/¯");
//
// try {
//
// throw exception;
//
// } finally {
// this.ex = exception;
// }
// }
// }
// }
