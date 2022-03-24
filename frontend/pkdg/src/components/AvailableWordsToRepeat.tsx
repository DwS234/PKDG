import { useEffect, useState } from "react";
import User from "../model/User";
import Word from "../model/Word";
import RepetitionsService from "../services/repetitions/RepetitionsService";
import WordsService from "../services/words/WordsService";

interface AvailableWordsToRepeatProps {
	user: User | null;
}

const AvailableWordsToRepeat = ({ user }: AvailableWordsToRepeatProps) => {
	const [availableWords, setAvailableWords] = useState<Array<Word>>([]);

	useEffect(() => {
		const getWordsToAddByUser = async () => {
			const availableWordsToRepeat = await WordsService.getAvailableWordsToRepeat(user!.username);
			setAvailableWords(availableWordsToRepeat);
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

	// TODO: Show word examples
	
	return (
		<div className="mt-5 flex justify-center">
			<a className="block min-w-[50%] p-6 pl-10 pr-10 max-w-[90%] max-h-[80%] bg-white rounded-lg border border-gray-200 shadow-md hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
				<h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
					Lista słów możliwych do dodania do powtórek
				</h5>
				{availableWords.map((availableWord) => (
					<div className="border-b border-black last:border-b-0 mb-2 pb-2 last:pb-0" key={availableWord.wordId}>
						<h4 className="font-bold inline">{availableWord.entry}</h4>
						<span> = {availableWord.definition}</span>
						<button
							onClick={() => addRepetition(availableWord.wordId)}
							className="ml-2 text-xs font-medium text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-full text-sm px-3 py-1.5 text-center dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
						>
							+
						</button>
					</div>
				))}
				{availableWords.length === 0 && <span>Brak słów możliwych do dodania do powtórek</span>}
			</a>
		</div>
	);
};

export default AvailableWordsToRepeat;
