package uk.co.zapp.samplezappmerchantapp.network.response;

import com.zapp.library.merchant.model.SettlementStatus;

/**
 * Enquire settlement status response data object.
 */
public class SettlementStatusResponse extends BaseServerResponse {

    /**
     * The settlement transaction status.
     */
    private SettlementStatus settlementStatus;

    public SettlementStatus getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(final SettlementStatus settlementStatus) {
        this.settlementStatus = settlementStatus;
    }
}
