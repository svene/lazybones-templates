package ${pkg}.registrations.boundary;

import ${pkg}.registrations.control.VatCalculator;
import ${pkg}.registrations.entity.Registration;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author airhacks.com
 */
@Stateless
public class Registrations {

    @PersistenceContext
    EntityManager em;

    @Inject
    VatCalculator priceCalculator;

    @Inject
    Logger tracer;

    public Registration register(Registration requestedRegistration) {
        tracer.info("registration arrived: " + requestedRegistration);
        Registration registration = em.merge(requestedRegistration);
        tracer.info("registration stored: " + requestedRegistration);
        registration.setCalculator(priceCalculator::calculateTotal);
        tracer.info("price computed: " + registration.getTotalPrice());
        return registration;
    }

    public Registration find(long registrationId) {
        return this.em.find(Registration.class, registrationId);
    }

}
