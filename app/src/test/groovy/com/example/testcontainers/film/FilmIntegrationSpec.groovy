package com.example.testcontainers.film

import com.example.testcontainers.TestContainersApplication
import com.example.testcontainers.film.domain.FilmFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@ActiveProfiles("it")
@SpringBootTest(classes = [TestContainersApplication.class])
class FilmIntegrationSpec extends Specification implements SampleFilms {

  @Autowired
  MockMvc mockMvc

  @Autowired
  FilmFacade filmFacade

  @WithMockUser
  def "should get films"() {
    given: "module has two films"
    filmFacade.add(commando, rambo)

    when: "client asks for all films"
    def films = mockMvc.perform(get("/film"))

    then: "module returns both films"
    films.andExpect(status().isOk())
    films.andExpect(content().json("""
            {
                "content": [
                    { "title": "$rambo.title" },
                    { "title": "$commando.title" }
                ]
            }
            """))

    when: "client asks for details of Rambo"
    def ramboFilmDetails = mockMvc.perform(get("/film/${rambo.title}"))

    then: "module return first Rambo"
    ramboFilmDetails.andExpect(status().isOk())
    ramboFilmDetails.andExpect(content().json("""
              {
                "title": "$rambo.title",
                "type": "$rambo.type"
              }
              """))
  }

}
