package org.mockito.configuration;


import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MockitoConfiguration extends DefaultMockitoConfiguration {
    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues() {
            @Override public Object answer(InvocationOnMock invocation) {
                Class<?> type = invocation.getMethod().getReturnType();
                if (type.isAssignableFrom(Observable.class)) {
                    return Observable.error(createException(invocation));
                } else if (type.isAssignableFrom(Single.class)) {
                    return Single.error(createException(invocation));
                } else if (type.isAssignableFrom(Completable.class))
                    return Completable.error(createException(invocation));
                else if (type.isAssignableFrom(Flowable.class))
                    return Flowable.error(createException(invocation));
                else
                    return super.answer(invocation);
            }
        };
    }

    private RuntimeException createException(InvocationOnMock invocationOnMock) {
        return new RuntimeException("No mock define for " + invocationOnMock.toString());
    }
}
