package examples.companies;

// first you should have added the WireMock dependencies to the pom file
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.intuit.karate.junit4.Karate;

@RunWith(Karate.class)
public class CompaniesRunner {

    private static final WireMockServer wireMockServer = new WireMockServer();

    private static final String URL = "/companies";

    @BeforeClass
    public static void setUp() {
        wireMockServer.start();

        configureFor("localhost", 8080);

        stubForGetAllCompanies();
    }

    private static void stubForGetAllCompanies() {
        stubFor(get(urlEqualTo(URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(getAllCompanies())));
    }

    private static String getAllCompanies() {
        return "[" + getParadigmaDigitalCompany() + ", " + getMinsaitCompany() + "]";
    }

    // this two methods will serve as different responses from the stub server
    // a different way to address this problem may be to define a jsonfile and refer to it
    private static String getParadigmaDigitalCompany() {
        return "{" +
                " \"cif\":\"B84946656\"," +
                " \"name\":\"Paradigma Digital\"," +
                " \"username\":\"paradigmadigital\"," +
                " \"email\":\"info@paradigmadigital.com\"," +
                " \"address\":{" +
                "    \"street\":\"Atica 4, Via de las Dos Castillas\"," +
                "    \"suite\":\"33\"," +
                "    \"city\":\"Pozuelo de Alarcon, Madrid\"," +
                "    \"zipcode\":\"28224\"" +
                " }," +
                " \"website\":\"https://www.paradigmadigital.com\"" +
                "}";
    }

    private static String getMinsaitCompany() {
        return "{" +
                " \"cif\":\"B82627019\"," +
                " \"name\":\"Minsait by Indra\"," +
                " \"username\":\"minsaitbyindra\"," +
                " \"email\":\"info@minsait.com\"," +
                " \"address\":{" +
                "    \"street\":\"Av. de Bruselas\"," +
                "    \"suite\":\"35\"," +
                "    \"city\":\"Alcobendas, Madrid\"," +
                "    \"zipcode\":\"28108\"" +
                " }," +
                " \"website\":\"https://www.minsait.com\"" +
                "}";
    }

    @AfterClass
    public static void tearDown() {
        wireMockServer.stop();
    }

}