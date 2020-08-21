package com.investec.rules;

import com.investec.models.Address;

import java.util.function.Predicate;

/**
 * Representation of a rule.
 * @param <T>
 */
public class Rule<T extends Address> {

    private final Predicate<T> condition;
    private final String message;

    /**
     * Construct the rule with a condition and message
     * @param condition predicate to be applied on the target
     * @param message rule message
     */
    public Rule(Predicate<T> condition, String message) {
        this.condition = condition;
        this.message = message;
    }

    /**
     * Test a rule against the target object.
     *
     * @param target object to be tested
     * @return {@code true} if the target passes the predicate
     *         otherwise false
     */
    public boolean evaluate(T target) {
        return condition.test(target);
    }

    /**
     * Message attached to the rule
     * @return rule message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Rule{" +
                " message='" + message +
                " }";
    }
}
