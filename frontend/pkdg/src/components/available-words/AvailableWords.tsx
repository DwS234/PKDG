import { useEffect, useState } from "react";
import User from "../../model/User";
import Word from "../../model/Word";
import RepetitionsService from "../../services/repetitions/RepetitionsService";
import WordsService from "../../services/words/WordsService";
import AvailableWord from "../available-word/AvailableWord";

interface AvailableWordsProps {
	user: User | null;
}

const AvailableWords = ({ user }: AvailableWordsProps) => {
	const [availableWords, setAvailableWords] = useState<Array<Word>>([]);

	useEffect(() => {
		const getWordsToAddByUser = async () => {
			try {
				const availableWordsToRepeat = await WordsService.getAvailableWordsToRepeat(user!.username);
				setAvailableWords(availableWordsToRepeat);
			} catch (e) {
				console.log(e);
			}
		};
		getWordsToAddByUser();
	}, []);

	const addRepetition = async (wordId: string) => {
		try {
			await RepetitionsService.addRepetition(wordId, user!.username);
			setAvailableWords(availableWords.filter((availableWord) => availableWord.wordId !== wordId));
		} catch (e) {
			console.log(e);
		}
	};

	return (
		<div className="mt-5 flex justify-center">
			<a className="block min-w-[50%] p-6 pl-10 pr-10 max-w-[90%] max-h-[80%] bg-white rounded-lg border border-gray-200 shadow-md dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
				<h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
					Lista słów możliwych do dodania do powtórek
				</h5>
				{availableWords.map((availableWord) => (
					<AvailableWord key={availableWord.wordId} word={availableWord} onAddHandler={addRepetition} />
				))}
				{availableWords.length === 0 && <span>Brak słów możliwych do dodania do powtórek</span>}
			</a>
		</div>
	);
};

export default AvailableWords;
