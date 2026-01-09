package no.uib.inf101.sample;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.TableView;

import model.CassinoModel;
import model.Deck;
import model.Hand;
import model.Table;
import view.CassinoView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Main {

	public static void main(String[] args) {
		CassinoModel model = new CassinoModel();

		JFrame frame = new JFrame("Cassino");
		CassinoView cassinoView = new CassinoView(model, frame);
		
		//Formating the game window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(cassinoView);
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
}