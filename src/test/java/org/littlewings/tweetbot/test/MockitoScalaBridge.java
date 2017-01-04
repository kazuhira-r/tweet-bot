package org.littlewings.tweetbot.test;

import org.mockito.Mockito;
import org.mockito.stubbing.Stubber;

public class MockitoScalaBridge {
    public static Stubber doSingleReturn(Object object) {
        return Mockito.doReturn(object);
    }
}
