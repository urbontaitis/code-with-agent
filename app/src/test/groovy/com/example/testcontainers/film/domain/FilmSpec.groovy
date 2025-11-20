package com.example.testcontainers.film.domain

import com.example.testcontainers.film.SampleFilms
import com.example.testcontainers.film.dto.FilmDto
import com.example.testcontainers.film.dto.FilmNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class FilmSpec extends Specification implements SampleFilms {

  FilmRepository filmRepository = Mock()
  FilmFacade filmFacade = new FilmConfiguration().filmFacade(filmRepository)

  def "should get film details"() {
    given: "module has film Rambo"
    var ramboFilm = new FilmCreator().create(rambo)
    filmRepository.findByTitle(rambo.title) >> Optional.of(ramboFilm)

    when: "client asks for details of Rambo"
    FilmDto foundFilm = filmFacade.showFilmDetails(rambo.title)

    then: "module returns details of Rambo"
    foundFilm.title == rambo.title
  }

  def "when no film found"() {
    given: "repository returns empty"
    filmRepository.findByTitle(rambo.title) >> Optional.empty()

    when: "client asks for details of Rambo"
    filmFacade.showFilmDetails(rambo.title)

    then: "module throws exception"
    def result = thrown(FilmNotFoundException)
    result.message == "No film of title $rambo.title found"
  }

  def "should get films"() {
    given: "module has two films"
    var ramboFilm = new FilmCreator().create(rambo)
    var commandoFilm = new FilmCreator().create(commando)
    var pageable = PageRequest.of(0, 10)
    filmRepository.findAll(pageable) >> new PageImpl<>([ramboFilm, commandoFilm])

    when: "client asks for films"
    Page<FilmDto> foundFilms = filmFacade.findFilms(pageable)

    then: "module returns both films"
    foundFilms.map{ it.title }.sort() == [rambo, commando]*.title.sort()
  }

}
