package com.application;

import com.application.model.Hall;
import com.application.model.Movie;
import com.application.model.Ticket;
import com.application.model.Visitor;
import com.application.service.HallService;
import com.application.service.MovieService;
import com.application.service.TicketService;
import com.application.service.VisitorService;

public class Main {
    public static void main(String[] args) {

        /* Добавление */
        //Залы
        HallService hallService = new HallService();

        Hall hall1 = new Hall("3D", 150);
        hallService.addHall(hall1);
        Hall hall2 = new Hall("2D", 200);
        hallService.addHall(hall2);
        Hall hall3 = new Hall("VIP", 30);
        hallService.addHall(hall3);
        Hall hall4 = new Hall("Atmos", 90);
        hallService.addHall(hall4);

        //Фильмы
        MovieService movieService = new MovieService();
        Movie movie1 = new Movie("Joker", 8, "Thriller");
        movieService.addMovie(movie1);
        Movie movie2 = new Movie("Interstellar", 8, "Sci-Fi");
        movieService.addMovie(movie2);
        Movie movie3 = new Movie("Avengers", 9, "Action");
        movieService.addMovie(movie3);
        Movie movie4 = new Movie("Inception", 10, "Action");
        movieService.addMovie(movie4);

        //Билеты
        TicketService ticketService = new TicketService();
        Ticket ticket1 = new Ticket(movie1, 350);
        ticketService.addTicket(ticket1);
        Ticket ticket2 = new Ticket(movie2, 250);
        ticketService.addTicket(ticket2);
        Ticket ticket3 = new Ticket(movie3, 250);
        ticketService.addTicket(ticket3);
        Ticket ticket4 = new Ticket(movie4, 250);
        ticketService.addTicket(ticket4);


        // Посетители
        VisitorService visitorService = new VisitorService();
        Visitor visitor1 = new Visitor("Ivan", "Ivanov");
        visitorService.addVisitor(visitor1);
        Visitor visitor2 = new Visitor("Andrey", "Smirnov");
        visitorService.addVisitor(visitor2);
        Visitor visitor3 = new Visitor("Anton", "Petrov");
        visitorService.addVisitor(visitor3);
        Visitor visitor4 = new Visitor("Maria", "Markova");
        visitorService.addVisitor(visitor4);

        /* Редактирование */
        //Залы
        hall1.setNumberOfSeats(100);
        hall1.setHallType("IMAX");
        hallService.editHall(hall1);

        //Фильмы
        movie2.setRating(10);
        movieService.editMovie(movie1);

        hall1.addMovie(movie1);
        hallService.editHall(hall1);
        movieService.editMovie(movie1);

        hall2.addMovie(movie2);
        hallService.editHall(hall2);
        movieService.editMovie(movie2);

        hall2.addMovie(movie3);
        hallService.editHall(hall1);
        movieService.editMovie(movie3);

        //Билеты
        ticket1.setCost(550);
        ticketService.editTicket(ticket1);

        //Посетители
        visitor1.setTicket(ticket1);
        visitorService.editVisitor(visitor1);
        visitor2.setTicket(ticket2);
        visitorService.editVisitor(visitor2);
        visitor3.setTicket(ticket3);
        visitorService.editVisitor(visitor3);
        visitor4.setTicket(ticket4);
        visitorService.editVisitor(visitor4);

        /* Вывод */
        //Залы
        System.out.println(hallService.findHalls());
        System.out.println(hallService.findHallById(1));

        //Фильмы
        System.out.println(movieService.findMovies());
        System.out.println(movieService.findMovieById(2));

        //Билеты
        System.out.println(ticketService.findTickets());
        System.out.println(ticketService.findTicketById(1));

        //Постеители
        System.out.println(visitorService.findVisitors());
        System.out.println(visitorService.findVisitorById(3));

        /* Удаление */
        //Залы
        hallService.deleteHall(hall4);

        //Фильмы
        movie2 = movieService.findMovieById(2);
        movieService.deleteMovie(movie2);

        //Билеты
        ticket3 = ticketService.findTicketById(3);
        ticketService.deleteTicket(ticket3);

        //Посетители
        visitorService.deleteVisitor(visitor4);
    }
}