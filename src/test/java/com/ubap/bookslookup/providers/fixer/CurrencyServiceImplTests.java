package com.ubap.bookslookup.providers.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubap.bookslookup.providers.fixer.model.Response;
import com.ubap.bookslookup.services.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTests {

    private static final String KEY = "dummy_key";

    private CurrencyService currencyService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.currencyService = new CurrencyServiceImpl(KEY, this.restTemplate);
    }

    @Test
    public void convertCurrency() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("PLN", new BigDecimal(2));
        rates.put("EUR", new BigDecimal(0.5));

        BigDecimal convertedCurrency = this.currencyService.convertCurrency(
                "PLN", "EUR", new BigDecimal(100), rates);

        assertEquals(0, new BigDecimal(25).compareTo(convertedCurrency));
    }

    @Test
    public void getRates() throws Exception {
        String rawResponse = "{\"success\":true,\"timestamp\":1540651145,\"base\":\"EUR\",\"date\":\"2018-10-27\",\"r" +
                "ates\":{\"AED\":4.196251,\"AFN\":86.083757,\"ALL\":124.897941,\"AMD\":555.160603,\"ANG\":2.028532,\"" +
                "AOA\":350.521246,\"ARS\":42.006639,\"AUD\":1.61131,\"AWG\":2.056403,\"AZN\":1.945019,\"BAM\":1.82117" +
                "8,\"BBD\":2.287519,\"BDT\":95.76027,\"BGN\":1.959756,\"BHD\":0.430543,\"BIF\":2073.53924,\"BMD\":1.1" +
                "42446,\"BND\":1.611021,\"BOB\":7.896301,\"BRL\":4.161707,\"BSD\":1.130964,\"BTC\":0.000178,\"BTN\":8" +
                "3.518507,\"BWP\":12.157956,\"BYN\":2.414336,\"BYR\":22391.938902,\"BZD\":2.297174,\"CAD\":1.500774,\"" +
                "CDF\":1840.480698,\"CHF\":1.139065,\"CLF\":0.028615,\"CLP\":785.550244,\"CNY\":7.932578,\"COP\":3634" +
                ".120288,\"CRC\":680.921034,\"CUC\":1.142446,\"CUP\":30.274815,\"CVE\":111.237102,\"CZK\":25.866921,\"" +
                "DJF\":203.035928,\"DKK\":7.475142,\"DOP\":55.928483,\"DZD\":136.183984,\"EGP\":20.421266,\"ERN\":17." +
                "137097,\"ETB\":32.107157,\"EUR\":1,\"FJD\":2.462017,\"FKP\":0.89112,\"GBP\":0.89024,\"GEL\":3.078937" +
                ",\"GGP\":0.890672,\"GHS\":5.541308,\"GIP\":0.89112,\"GMD\":56.556828,\"GNF\":10424.8189,\"GTQ\":8.85" +
                "4417,\"GYD\":238.879763,\"HKD\":8.961631,\"HNL\":27.544814,\"HRK\":7.447152,\"HTG\":81.824833,\"HUF\"" +
                ":324.889197,\"IDR\":17354.495239,\"ILS\":4.232745,\"IMP\":0.890672,\"INR\":83.541399,\"IQD\":1359.510" +
                "576,\"IRR\":48102.683437,\"ISK\":137.562347,\"JEP\":0.890672,\"JMD\":153.727956,\"JOD\":0.810684,\"J" +
                "PY\":127.83631,\"KES\":115.734111,\"KGS\":79.422384,\"KHR\":4650.897506,\"KMF\":493.936907,\"KPW\":1" +
                "027.807971,\"KRW\":1302.788577,\"KWD\":0.346984,\"KYD\":0.952394,\"KZT\":420.786097,\"LAK\":9756.488" +
                "063,\"LBP\":1729.720595,\"LKR\":197.540751,\"LRD\":178.878506,\"LSL\":16.628344,\"LTL\":3.373346,\"L" +
                "VL\":0.691055,\"LYD\":1.57701,\"MAD\":10.880545,\"MDL\":19.600377,\"MGA\":3998.560914,\"MKD\":61.865" +
                "773,\"MMK\":1810.834248,\"MNT\":2910.783217,\"MOP\":9.229939,\"MRO\":407.853568,\"MUR\":39.750837,\"" +
                "MVR\":17.662645,\"MWK\":830.723841,\"MXN\":22.119356,\"MYR\":4.769756,\"MZN\":69.237976,\"NAD\":16.6" +
                "04352,\"NGN\":414.708241,\"NIO\":36.791012,\"NOK\":9.539884,\"NPR\":134.169272,\"NZD\":1.754344,\"OMR" +
                "\":0.439847,\"PAB\":1.142617,\"PEN\":3.819772,\"PGK\":3.715406,\"PHP\":61.132706,\"PKR\":152.528375," +
                "\"PLN\":4.320674,\"PYG\":6862.6156,\"QAR\":4.159764,\"RON\":4.669066,\"RSD\":118.666278,\"RUB\":75.0" +
                "47695,\"RWF\":999.64013,\"SAR\":4.285433,\"SBD\":9.374397,\"SCR\":15.583387,\"SDG\":53.83034,\"SEK\"" +
                ":10.43362,\"SGD\":1.577,\"SHP\":1.509061,\"SLL\":9653.667924,\"SOS\":663.761433,\"SRD\":8.520405,\"S" +
                "TD\":24049.170424,\"SVC\":10.000176,\"SYP\":588.360005,\"SZL\":16.628343,\"THB\":37.697862,\"TJS\":1" +
                "0.771494,\"TMT\":3.998561,\"TND\":3.268885,\"TOP\":2.617972,\"TRY\":6.38959,\"TTD\":7.702085,\"TWD\"" +
                ":35.390121,\"TZS\":2618.26158,\"UAH\":32.308789,\"UGX\":4300.566502,\"USD\":1.142446,\"UYU\":37.5983" +
                "12,\"UZS\":9385.193141,\"VEF\":283920.589007,\"VND\":26682.965561,\"VUV\":128.874746,\"WST\":3.04756" +
                "3,\"XAF\":660.539766,\"XAG\":0.077709,\"XAU\":0.000926,\"XCD\":3.087518,\"XDR\":0.82564,\"XOF\":667." +
                "188763,\"XPF\":120.242843,\"YER\":286.011737,\"ZAR\":16.682113,\"ZMK\":10283.387801,\"ZMW\":13.21128" +
                "7,\"ZWL\":368.273149}}";

        ObjectMapper objectMapper = new ObjectMapper();
        Response response  = objectMapper.readValue(rawResponse, Response.class);

        Mockito.when(this.restTemplate.getForEntity(any(String.class), any())).thenAnswer(
                invocation -> {
                    String url = invocation.getArgument(0);
                    assertEquals("http://data.fixer.io/api/latest?access_key=dummy_key", url);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                });

        Map<String, BigDecimal> rates = this.currencyService.getRates();

        assertEquals(0, new BigDecimal("4.196251").compareTo(rates.get("AED")));
        assertEquals(0, new BigDecimal("1.142446").compareTo(rates.get("USD")));
        assertEquals(0, new BigDecimal("4.320674").compareTo(rates.get("PLN")));

    }
}
