package com.Tasko.Registration.dto;

public class ClientCountsDTO
{
    private Long totalClientCount;
    private Long incomeTaxClientCount;

    public Long getTotalClientCount() {
        return totalClientCount;
    }

    public void setTotalClientCount(Long totalClientCount) {
        this.totalClientCount = totalClientCount;
    }

    public Long getIncomeTaxClientCount() {
        return incomeTaxClientCount;
    }

    public void setIncomeTaxClientCount(Long incomeTaxClientCount) {
        this.incomeTaxClientCount = incomeTaxClientCount;
    }

    public ClientCountsDTO(Long totalClientCount, Long incomeTaxClientCount)
    {
        this.totalClientCount = totalClientCount;
        this.incomeTaxClientCount = incomeTaxClientCount;
    }

    public ClientCountsDTO() {
    }
}