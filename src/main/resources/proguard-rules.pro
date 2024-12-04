# Preservar as classes do Spring
-keep class org.springframework.** { *; }

# Preservar a classe de entrada
-keep public class com.github.bat333.stockroom.StockroomApplication { public static void main(java.lang.String[]); }

# Não ofuscar classes que são usadas por reflexão
-keepclassmembers class * {
    public static void main(java.lang.String[]);
}

# Manter as anotações do Lombok
-keepclassmembers class * {
    @org.springframework.stereotype.*;
}
