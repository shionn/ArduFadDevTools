import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RoomConvert {

	// rien 00
	// mur 01
	// porte 10
	// sortie sans porte 11

	// rien 0000
	// Passage 0001
	// porte H 0010
	// porte V 0011
	// mur-H 0100
	// mur-V 0101
	// mur-C 0110

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		new RoomConvert().start();
	}

	private int currentByte = 8;
	private int room = 0;

	private void start() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/rooms"));
				BufferedWriter writer = new BufferedWriter(new FileWriter("out"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.matches("[0-9 ]*")) {
					finishCurrentRoom(writer);
					room++;
					writer.append("\n\t" + room + ", ").append(line.replace(" ", ", ")).append(", ");
				} else {
					for (char c : line.toCharArray()) {
						if (currentByte == 8) {
							writer.append("0b");
						}
						// rien 00
						// Passage 01
						// porte H/V 10
						// mur 11

						switch (c) {
						case ' ':
							writer.append("00");
							break;
						case 'S':
							writer.append("01");
							break;
						case 'P':
							writer.append("10");
							break;
						case 'M':
							writer.append("11");
							break;
						}
						currentByte -= 2;
						if (currentByte == 0) {
							writer.append(", ");
							currentByte = 8;
						}
					}
				}
			}
			finishCurrentRoom(writer);
		}
	}

	private void finishCurrentRoom(BufferedWriter writer) throws IOException {
		if (currentByte != 8) {
			while (currentByte != 0) {
				writer.append("00");
				currentByte -= 2;
			}
			currentByte = 8;
			writer.append(", ");
		}
	}
}
