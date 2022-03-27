const GRAY_PAGE_NUMBER_CLASS =
	"cursor-pointer py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white";
const CURRENT_PAGE_NUMBER_CLASS =
	"cursor-pointer py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white";

interface PaginationProps {
	currentPage: number;
	itemsPerPage: number;
	totalItemCount: number;
	paginate: (pageNumber: number) => void;
}

const Pagination = ({ currentPage, itemsPerPage, totalItemCount, paginate }: PaginationProps) => {
	const pageNumbers: Array<number> = [];

	for (let i = 1; i <= Math.ceil(totalItemCount / itemsPerPage); i++) {
		pageNumbers.push(i);
	}

	return (
		<nav>
			<ul className="inline-flex -space-x-px">
				{pageNumbers.map((number) => (
					<li key={number}>
						<a
							href={void 0}
							className={number === currentPage ? CURRENT_PAGE_NUMBER_CLASS : GRAY_PAGE_NUMBER_CLASS}
							onClick={() => paginate(number)}
						>
							{number}
						</a>
					</li>
				))}
			</ul>
		</nav>
	);
};

export default Pagination;
