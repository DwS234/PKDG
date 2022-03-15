import { ChangeEvent, useState } from "react";
import Word from "../model/Word";
import WordsService from "../services/words/WordsService";
import WordPopup from "./WordPopup";

const WordsSearch = () => {

  const [autocomplete, setAutocomplete] = useState<Array<string>>([]);
  const [autocompleteVisible, setAutocompleteVisible] = useState(false);
  const [wordsPopupOpen, setWordsPopupOpen] = useState(false);
  const [words, setWords] = useState<Array<Word>>([]);

  const onSearchChange = async (ev: ChangeEvent<HTMLInputElement>) => {
    const query = ev.currentTarget.value;
    if (query === "") {
      setAutocomplete([]);
      setAutocompleteVisible(false);
      return;
    }
    const autocompleteData = await WordsService.getAutocomplete(query);
    setAutocomplete(autocompleteData);
    setAutocompleteVisible(true);
  }

  const onSearchInputBlur = () => {
    setAutocompleteVisible(false);
  }

  const onSearchInputFocus = () => {
    if (autocomplete.length > 0) {
      setAutocompleteVisible(true);
    }
  }

  const onClickAutocompleteHint = async (autocompleteHint: string) => {
    const words = await WordsService.getWordsByEntry(autocompleteHint);
    setWords(words);
    setWordsPopupOpen(true);
  }

  return <>
      <input type="text" onBlur={onSearchInputBlur} onFocus={onSearchInputFocus} onChange={onSearchChange} className="block p-2 pl-10 w-full text-gray-900 bg-gray-50 rounded-lg border border-gray-300 sm:text-sm focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Szukaj..." />
      <WordPopup
        open={wordsPopupOpen}
        onClose={() => setWordsPopupOpen(false)} 
        words={words}
      />
      {
        autocompleteVisible && (
          <div className="dropdown-menu absolute top-10 left-0 w-full block p-2 pl-2 w-full text-gray-900 bg-gray-50 rounded-lg border border-gray-300 sm:text-sm focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
            {
              autocomplete.length > 0 &&
                autocomplete.map(a => (
                  <div key={a} onMouseDown={() => onClickAutocompleteHint(a)} className="cursor-pointer hover:text-blue-700 border-b border-black last:border-b-0">
                    {a}
                  </div>
                ))
            }
            {
              autocomplete.length === 0 && <div>Brak rezultatów</div>
            }
        </div>
        )
      }
    </>;
}

export default WordsSearch;
