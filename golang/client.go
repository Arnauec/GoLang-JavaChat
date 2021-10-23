package main

// Arnau EC - 2016

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"os"
	"strings"
	"sync"
)

// S'ha de posar host:port com a primer argument quan executem l'arxiu
// Exemple : localhost:50000

var llistaUsuaris []string
var nomUsuari string
var nomSeparat []string
var wg sync.WaitGroup

func init() {
	nf, err := os.Create("log.txt")
	if err != nil {
		fmt.Println(err)
	}
	log.SetOutput(nf)
}

func main() {

	wg.Add(2)
	reader := bufio.NewReader(os.Stdin)
	fmt.Print("Introdueix el teu nom d'usuari: ")

	nomUsuari, _ = reader.ReadString('\n')
	nomSeparat = strings.Split(nomUsuari, "\n")

	var conn, _ = net.Dial("tcp", os.Args[1])

	fmt.Fprintf(conn, nomSeparat[0]+"\n")

	go func() {
		for {
			// read in input from stdin
			text, err := reader.ReadString('\n')
			if err != nil {
				log.Fatalln(err)
			}
			// send to socket
			if strings.Contains(text, "/userslist") {
				retrieveList()
			} else {
				fmt.Fprintf(conn, nomSeparat[0]+": "+text)
			}
		}
		wg.Done()
	}()

	go func() {
		for {
			// listen for reply
			message, err := bufio.NewReader(conn).ReadString('\n')
			if err != nil {
				log.Fatalln(err)
			}
			if strings.Contains(message, "/newuser") {
				talls := strings.Split(message, " ")
				addToList(talls[1])
			} else if strings.Contains(message, "/deleteuser") {
				talls := strings.Split(message, " ")
				removeFromList(talls[1])
			} else {
				fmt.Print(message)
			}

		}
		wg.Done()
	}()
	wg.Wait()

}

func addToList(nom string) {
	llistaUsuaris = append(llistaUsuaris, nom)
}

func removeFromList(nom string) {
	for v := range llistaUsuaris {
		if strings.Compare(llistaUsuaris[v], nom) == 0 {
			llistaUsuaris[v] = llistaUsuaris[len(llistaUsuaris)-1] // Replace it with the last one.
			llistaUsuaris = llistaUsuaris[:len(llistaUsuaris)-1]   // Chop off the last one.
			break
		}
	}
}

func retrieveList() {
	fmt.Println("Llista d'usuaris conectats:")
	for v := range llistaUsuaris {
		fmt.Print(llistaUsuaris[v])
	}
}
