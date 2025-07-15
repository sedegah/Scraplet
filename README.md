# Scraplet

Scraplet is a simple Java-based command-line web scraper that fetches a webpage, saves the HTML, and generates a structured JSON representation of its DOM.

## 🔧 Features

- Fetches any webpage using a user-defined URL
- Saves the full raw HTML to a file
- Parses and converts the DOM into a structured JSON format
- Outputs both files in the local working directory
- Built using `jsoup` for parsing and `gson` for JSON serialization

---

##  Requirements

- Java JDK 11 or higher
- `jsoup-1.21.1.jar`
- `gson-2.10.1.jar`

Download the libraries:
- [jsoup](https://jsoup.org/download)
- [gson](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/)

Place both `.jar` files in the same directory as `Scraplet.java`.

---

##  How to Compile

```bash
javac -cp ".;jsoup-1.21.1.jar;gson-2.10.1.jar" Scraplet.java
````

---

##  How to Run

```bash
java -cp ".;jsoup-1.21.1.jar;gson-2.10.1.jar" Scraplet <URL> <output.json> <output.html>
```

### Example

```bash
java -cp ".;jsoup-1.21.1.jar;gson-2.10.1.jar" Scraplet https://example.com example.json example.html
```

---

##  Output

* `example.html`: raw HTML of the page
* `example.json`: structured DOM in JSON format (tag names, text, children)

---

##  Notes

* The tool uses `Mozilla/5.0` as the User-Agent to avoid basic bot blocks.
* It processes and serializes the DOM recursively.

---

## Use Cases

* Lightweight content extraction
* Web scraping starter projects
* Feeding structured HTML into ML/NLP pipelines
* Crawling and archiving public web pages

---

##  License

MIT License

