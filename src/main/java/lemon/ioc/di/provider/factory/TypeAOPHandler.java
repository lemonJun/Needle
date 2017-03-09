package lemon.ioc.di.provider.factory;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lemon.ioc.aop.AOP;
import lemon.ioc.aop.AOPController;
import lemon.ioc.di.Utils;
import lemon.ioc.di.binder.Scope;
import lemon.ioc.di.exception.AnnoHandleException;
import lemon.ioc.di.provider.HandlerChain;
import lemon.ioc.di.provider.TypeAnnotationHandler;

public class TypeAOPHandler implements TypeAnnotationHandler {

    private static final Logger logger = LoggerFactory.getLogger(TypeAOPHandler.class);

    @Override
    public boolean canHandle(Annotation[] annotations) {
        return Utils.getAnno(AOP.class, annotations) != null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Object handle(Scope scope, Class<?> cls, Class<?> expectedClass, HandlerChain chain) throws AnnoHandleException {
        logger.debug("Entered TypeAOPHandler with args: \n\tcls:\t{}\n\tchain:\t{}", cls, chain);
        Object o = chain.next().handle(scope, cls, expectedClass, chain);
        logger.debug("start handling with TypeAOPHandler");
        logger.debug("retrieved instance is {}", o);
        if (o == null)
            throw new AnnoHandleException("cannot weave to null");
        Object r = AOPController.weave(scope, () -> o, (Class<Object>) expectedClass);
        logger.debug("generated proxy object is {}", r);
        return r;
    }

}
