package com.grupo6.realestate.entity.enums;

public enum ListingStatus {
    AVAILABLE, // disponible para la venta
    FOR_SALE, // en venta o alquiler
    RENTED, // rentada
    UNDER_CONTRACT, // ya esta lista para tramites
    CONTINGENT, // comprada pero con inspecciones
    SOLD, // vendida
    TEMPORARILY_OFF_MARKET, // no se puede vender porque no esta el dueno o algo asi
    UNDER_REPAIR // en reparaciones
}
