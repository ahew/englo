package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

public interface IProjectFrontAction {

	/**
	 * 
	 * @return
	 */
	public String save();

	/**
	 * 
	 * @return
	 */
	public String view();

	/**
	 * 
	 * @return
	 */
	@Deprecated
	public String edit();
	
	/**
	 * 
	 * @return
	 */
	public String saveExit();

	/**
	 * 
	 * @return
	 */
	public String newProject();

	/**
	 * 
	 * @return
	 */
	public String trash();

	/**
	 * 
	 * @return
	 */
	public String delete();
        
        /**
         * 
         * @return 
         */
        public String document();
        
        /**
         * 
         * @return 
         */
        public String upload();
        
        
        /**
         * 
         * @return 
         */
        public String documentTrash();
        
        
        /**
         * 
         * @return 
         */
        public String documentDelete();
}
