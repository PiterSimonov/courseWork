//package simonov.hotel.utilites;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import simonov.hotel.services.interfaces.BookingService;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class BookingControl {
//    @Autowired
//    BookingService bookingService;
//
//    public BookingControl() {
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleWithFixedDelay((Runnable) () -> {
//            bookingService.deleteHistoryBookings();
//        }, 0, 1, TimeUnit.DAYS);
//    }
//}
