# YTS Downloader
A [YTS YIFY movies](https://yts.ag/) torrent files downloader.

![main window](https://cloud.githubusercontent.com/assets/8356445/14646154/0fe7d3f6-061e-11e6-994b-3aafff180dd3.png)

## About
You may wonder, what's the advantage between using this program and visiting the official website. Well, the main advantage is that you can download several torrent files with just one action.

The idea for making this program came up because, while browsing [YTS YIFY](https://yts.ag/), I was looking for action movies with a score 8+ and there were more than 100 results. Due to it wasn't practical going one by one and downloading each torrent file, I thought "there has to be a better way to download several files", and this program was the result.

Fun story: during the final phase of v1.0 of this program, while visiting [YTS YIFY](https://yts.ag/), I saw something on the bottom of the page that I haven't been aware of before... a link to their API docs. I made this whole thing using a bot that goes to each page of results and parses the html code to extract the data relevant to the movie, because I didn't know there was an open API :sweat_smile:. Maybe in a later version I'll incorporate the API functionality...

## Features
- Download multiple torrent files just by pressing one button.
- Download 720p and 1080p torrent files (when available).
- Search filters.
- Select just the movies and files that you want to download.
- Includes GUI.

## License
[The MIT License (MIT)](https://opensource.org/licenses/MIT)
