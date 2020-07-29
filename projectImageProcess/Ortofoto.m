classdef Ortofoto
    % Ortofoto create an object of Ortofoto image
    % Calculate NDVI (Normalized Difference Vegetation Index) and with
    % threshold
    % Calculate NDWI (Normalized Difference Water Index) and with threshold
    
    properties
        imData
    end
    methods
        function obj = Ortofoto(imData)
            obj.imData = imData;
        end

        function ndvi = getNDVI(~, nir, red)
            ndvi = (nir - red) ./ (nir + red);
        end
       
        function ndvi_t = getNDVIThreshold(~, ndvi, gt)
            % gt = Calculates and stores the optimal threshold in range 0-1
            % by using global adaptive threshold.
            gt = 0.0000001;
            ndvi_t = (ndvi <= gt);
        end

        function ndwi = getNDWI(~, nir, green)
            ndwi = (green - nir) ./ (green + nir);
        end

        function ndwi_t = getNDWIThreshold(~, ndwi, gt)
            % gt = Calculates and stores the optimal threshold in range 0-1
            % by using global adaptive threshold.
            gt = 0.095;
            ndwi_t = (ndwi <= gt);
        end

        function result = getResultOrto(~, ndvi_t, ndwi_t, red)
            result = ndvi_t .* ndwi_t .* red;
        end
    end
end