package ru.iteco.fmhandroid.ui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthorisationTest.class,
        Claim.class,
        Main.class,
        Menu.class,
        News.class
})
public class JunitTestSuite{}
