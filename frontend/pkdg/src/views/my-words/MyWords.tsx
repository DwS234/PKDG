import { useEffect, useState } from "react";
import RepetitionWithWordBasic from "../../model/RepetitionWIthWordBasic";
import User from "../../model/User";
import RepetitionsService from "../../services/repetitions/RepetitionsService";

interface MyWordsProps {
	user: User | null;
}

const MyWords = ({ user }: MyWordsProps) => {
	const [repetitionsWithWords, setRepetitionsWithWords] = useState<Array<RepetitionWithWordBasic>>([]);

	useEffect(() => {
		(async function () {
			try {
				const repetitions = await RepetitionsService.getRepetitionsByUsername(user!.username);
				setRepetitionsWithWords(repetitions);
			} catch (e) {
				console.error(e);
			}
		})();
	}, []);

	const deleteRepetiton = async (repetitionId: string) => {
		try {
			await RepetitionsService.deleteRepetiton(repetitionId);
			setRepetitionsWithWords(repetitionsWithWords.filter((r) => r.repetition.repetitionId !== repetitionId));
		} catch (e) {
			console.log(e);
		}
	};

	return (
		<div className="mt-5 flex justify-center">
			<a className="block min-w-[50%] p-6 pl-10 pr-10 max-w-[90%] max-h-[80%] bg-white rounded-lg border border-gray-200 shadow-md hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700">
				<h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
					Lista wszystkich słów dodanych do powtórek
				</h5>
				{repetitionsWithWords.map((repetitionWithWord) => (
					<div
						className="border-b border-black last:border-b-0 mb-2 pb-2 last:pb-0"
						key={repetitionWithWord.repetition.repetitionId}
					>
						<h4 className="font-bold inline">{repetitionWithWord.word.entry}</h4>
						<span> = {repetitionWithWord.word.definition}</span>
						<button
							onClick={() => deleteRepetiton(repetitionWithWord.repetition.repetitionId!)}
							className="ml-2 text-xs font-medium text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-full text-sm px-3 py-1.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900"
						>
							-
						</button>
						<br />
						<span>Liczba powtórek: {repetitionWithWord.repetition.timesSeen}</span>
						<br />
						<span>Następna powtórka: {repetitionWithWord.repetition.nextDate}</span>
						<div>
							Łatwość:
							<div className="w-48 bg-gray-200 rounded-full h-2.5 dark:bg-gray-700">
								<div
									className="bg-blue-600 h-2.5 rounded-full"
									style={{ width: `${Math.round((repetitionWithWord.repetition.easiness * 100) / 2.5)}%` }}
								></div>
							</div>
						</div>
					</div>
				))}
				{repetitionsWithWords.length === 0 && <span>Brak słów dodanych do powtórek</span>}
			</a>
		</div>
	);
};

export default MyWords;
