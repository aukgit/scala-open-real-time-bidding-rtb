DELETE 
FROM
	BidRequest;
DELETE 
FROM
	BidResponse;
DELETE 
FROM
	LogTrace;
	
	
UPDATE sqlite_sequence
	SET seq = 0 
	 
WHERE
	sqlite_sequence.name = 'BidResponse' 
	OR sqlite_sequence.name = 'LogTrace' 
	OR sqlite_sequence.name = 'BidRequest' 