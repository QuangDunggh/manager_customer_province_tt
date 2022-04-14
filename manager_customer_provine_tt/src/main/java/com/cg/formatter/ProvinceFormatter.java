package com.cg.formatter;

import com.cg.model.Province;
import com.cg.service.province.IProvinceService;
import com.cg.service.province.ProvinceService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class ProvinceFormatter implements Formatter<Province> {
    private IProvinceService provinceService;

    public ProvinceFormatter(IProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Override
    public Province parse(String id, Locale locale) throws ParseException {
        Optional<Province> optionalProvince = provinceService.findById(Long.parseLong(id));
        return optionalProvince.orElse(null);
    }

    @Override
    public String print(Province province, Locale locale) {
        return null;
    }
}
