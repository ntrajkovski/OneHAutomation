package utils;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.UUID;

import static java.lang.Thread.sleep;
import io.restassured.*;
public class Operations {
    public static String getUniqueID()
    {
        return UUID.randomUUID().toString();
    }

    public static void GetStatusCode(String s) {
      //  return RestAssured.get(url).statusCode();
    }
}
