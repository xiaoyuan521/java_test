package person.zhao.anno.mapbean;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeyAnno {

    // 注解的2个方法, value -> 值， pattern -> 类型为日期时候，指定pattern
    public String value();

    public Pattern pattern() default Pattern.NORMAL;

    // 日期类型的枚举
    enum Pattern {
        NORMAL("yyyy-MM-dd HH:mm:ss"), SHORT("yyyy-MM-dd");

        private String name = null;

        private Pattern(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
