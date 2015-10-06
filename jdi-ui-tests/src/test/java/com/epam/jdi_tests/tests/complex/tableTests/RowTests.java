package com.epam.jdi_tests.tests.complex.tableTests;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.Rows;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ICell;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table.interfaces.ITable;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.logger;

/**
 * Created by Natalia_Grebenshikova on 10/05/2015.
 */
public class RowTests extends InitTableTests {

    @Test
    public void findAllRows(){
        Rows rows = support().rows();
        Assert.areEquals(rows.count(), 6, String.format("Expectde to find 6 rows, but was found %d", rows.count()));
    }
    @Test
    public void findAllRowsWithRepeatableWord(){
        MapArray<String, MapArray<String, ICell>> rows = support().rows("Plans=Cucumber, Jbehave, Thucydides, SpecFlow");

        Assert.areEquals(rows.size(),1,String.format("Number of found element expected to 1, but was %d", rows.size()));
        Assert.areEquals(rows.key(0),"6",String.format("Expected row number is '6', but was '%s'", rows.key(0)));
    }
    @Test
    public void findAllRowsWithSameValue(){
        MapArray<String, MapArray<String, ICell>> rows = support().rows("Plans=MSTest, NUnit, Epam");

        Assert.areEquals(rows.size(),2,String.format("Number of found element expectde to 2, but was %d", rows.size()));

        Assert.areEquals(rows.key(0),"2",String.format("Expected row 1 number is '2', but was '%s'", rows.key(0)));
        Assert.areEquals(rows.key(1),"3",String.format("Expected row 2 number is '3', but was '%s'", rows.key(1)));
    }

    @Test
    public void getRowsCacheTest() {
        List<ICell> cells = support().getCells();
        Timer timer = new Timer();
        support().useCache();
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        logger.info("[TIME]: " + timer.timePassedInMSec()+"");
        Assert.areEquals(rows,
                "1:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "3:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "4:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                        "5:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:" +
                        "Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow");
    }
    @Test
    public void getRowsTest() {
        Timer timer = new Timer();
        MapArray<String, MapArray<String, String>> rows = support().rows().getAsText();
        logger.info("[TIME]: " + timer.timePassedInMSec()+"");
        Assert.areEquals(rows,
                "1:" +
                        "Type:Drivers, " +
                        "Now:Selenium Custom, " +
                        "Plans:JavaScript, Appium, WinAPI, Sikuli, " +
                        "2:" +
                        "Type:Test Runner, " +
                        "Now:TestNG, JUnit Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "3:" +
                        "Type:Asserter, " +
                        "Now:TestNG, JUnit, Custom, " +
                        "Plans:MSTest, NUnit, Epam, " +
                        "4:" +
                        "Type:Logger, " +
                        "Now:Log4J, TestNG log, Custom, " +
                        "Plans:Epam, XML/Json logging, Hyper logging, " +
                        "5:" +
                        "Type:Reporter, " +
                        "Now:Jenkins, Allure, Custom, " +
                        "Plans:EPAM Report portal, Serenity, TimCity, Hudson, " +
                        "6:" +
                        "Type:BDD/DSL, " +
                        "Now:Custom, " +
                        "Plans:Cucumber, Jbehave, Thucydides, SpecFlow");
    }

    @Test
    public void verifyCellsRowByRowNumber(){
        List<String> expectedRowsValue = Arrays.asList("Asserter", "TestNG, JUnit, Custom", "MSTest, NUnit, Epam");

        MapArray<String, ICell> cellRows = support().row(3);

        Assert.areEquals(cellRows.count(),3, String.format("Number of cell expected to be 3, but was ",cellRows.count()));

        for (int i=0; i<3; i++)
            Assert.areEquals(   cellRows.value(i).getValue(),
                    expectedRowsValue.get(i),
                    String.format("Expected content for row %d is '%s', but was %s", i+1, expectedRowsValue.get(i), cellRows.value(i).getValue()));

    }
    @Test
    public void verifyCellsRowByRowName(){
        List<String> expectedRowsValue = Arrays.asList("Asserter", "TestNG, JUnit, Custom", "MSTest, NUnit, Epam");

        MapArray<String, ICell> cellRows = support().row("3");

        Assert.areEquals(cellRows.count(),3, String.format("Name of cell expected to be 3, but was ",cellRows.count()));

        for (int i=0; i<3; i++)
            Assert.areEquals(   cellRows.value(i).getValue(),
                    expectedRowsValue.get(i),
                    String.format("Expected content for row %d is '%s', but was %s", i+1, expectedRowsValue.get(i), cellRows.value(i).getValue()));

    }

    @Test
    public void verifyRowCount(){
        int actualRowCount = support().rows().count();

        Assert.areEquals(actualRowCount, 6, String.format("Expected number of rows is 6, but found %d", actualRowCount));
    }

}