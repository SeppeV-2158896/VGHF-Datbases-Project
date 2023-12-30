package be.vghf.enums;

public enum State {AWAY, RETURNED, OUT_STANDING_FINE}

// if return date = null -> away
// if return date not null, and return date - loaned date =< term = returned
// if return date not null & fine = null, and return date - loaned date > term = outstanding fine
// if return date not null & fine not null, and return date - loaned date > term = returned
