# README #
### Key-to-Voice ###

* Android aplikace cílená na tablety.
* Pro správnou funkčnost vyžaduje TTS engine nanistalovaný na cílovém zařízení.

### Info ###

* Vývoj je připravený pro AndroidStudio
* Kompletně napsaná v Kotlinu (https://kotlinlang.org/)
* Celá aplikace je navržená ve style Model-View-Presenter. Jednotlivé komponenty spolu komunikují s pomocí knihovny EventBus (https://github.com/greenrobot/EventBus) Díky tomu je celý kód výrazně zjednodušený, protože není nutné ručně předávat události.
* Dependency Injection je řešená pomocí knihovny Kodein (https://github.com/SalomonBrys/Kodein). Ta zajistí propojení MainApplication a KeyboardPresenter třídy.
* Zobrazení jednotlivých vět/slov zajišťuje FlowLayout (https://github.com/blazsolar/FlowLayout).

* věty a fráze jsou načítány ze souboru tree.yml, který se nachází v adresáři res/raw/. Jedná se o stromovou strukturu, zapsanou v jazyce YAML.
* Veškeré ikony v aplikaci vznikly konverzí z SVG ikon, s pomocí konvertoru v Android studiu.