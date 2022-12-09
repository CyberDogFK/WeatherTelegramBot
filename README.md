# WeatherTelegramBot

This bot works through Telegram and can show the weather in any location just by its name,
along with a gif image, for greater usability. 
(When the user sees not only the weather but also some funny GIFs,
it will be much more interesting for him to view the information again through the bot.)

The bot is written using Java and the Telegram API.
Weather information is pulled from OpenWeatherMap in JSON format.

Based on the weather value, a picture is selected from the map according to the type of weather 
(rain, snow, sunny, fog). 
There are several variations of gifs for different weathers, and they are chosen randomly from the set.

Gif images are stored on Telegram servers and are received by hash code.
