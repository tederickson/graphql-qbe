-- Alexandre Dumas   "The Man in the Iron Mask"
INSERT INTO public.comment(book_id, name, content, published_on, updated_on)
	VALUES
	(3, 'george', 'Such a twist!', '1944-11-25', null),
	(3, 'carl', 'The victim of betrayal by his own brother', '1962-08-05', NOW()),
	(3, 'roberto', 'The Musketeers ride again', '2022-12-05', NOW());