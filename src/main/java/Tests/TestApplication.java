package Tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestApplication {


    public static void main(String[] args) {

        EntityManagerFactory twitter_clone =
                Persistence.
                        createEntityManagerFactory("twitter_clone");
        EntityManager entityManager = twitter_clone.createEntityManager();
        entityManager.close();
        twitter_clone.close();



        Date n = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        System.out.println(simpleDateFormat.format(n));
        System.out.println(n.toString());
//        System.out.println(dat.datgetTime());
//        در این تمرین قصد داریم توئی تر را با ا مکانات زیر شبیه سازی کنیم:
//        1 .امکان اضافه، مشاهده، حذف و ویرایش کردن حساب کاربری

//        2 .امکان اضافه، مشاهده، حذف و ویرایش کردن توئیت


//• متن هر توئیت دارای محدودیت تعداد حروف می باشد )تعداد بیشترین کاراکتر: 280)

//        3 .امکان مشاهده توئیت تمامی کاربران

//        4 .امکان اضافه، مشاهده، حذف و ویرایش کردن نظر)comment )برای هر توئیت

//        5 .امکان الیک کردن توئیت)like )

//        6 .امکان برداشتن الیک از توئیت(dislike(

//                7 .امکان مشاهده مشخصات کامل توئیت که شامل موارد زیر می باشد:
//• متن توئیت
//• تعداد الیک ها
//• کامنت های درج شده برای توئیت به همراه نام کاربری )نام کاربری حسابی که کامنت را درج کرده(

//                8 .امکان جستجو حساب کاربری بر اساس نام کاربری
    }
}
